package com.vno;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

import java.util.Set;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import com.vno.auth.dto.*;
import com.vno.auth.entity.User;
import com.vno.security.jwt.JwtService;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.mindrot.jbcrypt.BCrypt;

// import java.util.Set;

@Path("/api/auth")
@RequestScoped
public class AuthResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    JwtService jwtService;

    @Inject
    @Claim(standard = Claims.birthdate)
    String birthdate;

    @GET
    @Path("permit-all")
    @Transactional
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@Context SecurityContext ctx) {
        return getResponseString(ctx);
    }

    @GET
    @Path("roles-allowed")
    @RolesAllowed({ "User", "Admin" })
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowed(@Context SecurityContext ctx) {
        return getResponseString(ctx) + ", birthdate: " + jwt.getClaim("birthdate").toString();
    }

    @GET
    @Path("roles-allowed-admin")
    @RolesAllowed("Admin")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public String helloRolesAllowedAdmin(@Context SecurityContext ctx) {
        return getResponseString(ctx) + ", birthdate: " + birthdate;
    }

    @GET
    @Path("deny-all")
    @DenyAll
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public String helloShouldDeny(@Context SecurityContext ctx) {
        throw new InternalServerErrorException("This method must not be invoked");
    }

    private String getResponseString(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        return String.format("hello 1212 + %s,"
                + " isHttps: %s,"
                + " authScheme: %s,"
                + " hasJWT: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt());
    }

    private boolean hasJwt() {
        return jwt.getClaimNames() != null;
    }

    @POST
    @Path("/register")
    @PermitAll
    @Transactional
    @Operation(summary = "Đăng ký tài khoản mới")
    @APIResponse(responseCode = "201", description = "Đăng ký thành công")
    public Response register(@Valid RegisterRequest req) {

        if (User.count("email", req.email()) > 0) {
            return Response.status(400).entity("Email đã tồn tại").build();
        }

        User user = new User();
        user.email = req.email();
        user.password = BCrypt.hashpw(req.password(), BCrypt.gensalt());
        user.name = req.name() != null ? req.name() : req.email().split("@")[0];
        user.persist();

        return Response.status(201).entity("Đăng ký thành công").build();
    }

    @POST
    @Path("/login")
    @PermitAll
    @Operation(summary = "Đăng nhập tài khoản")
    public Response login(@Valid LoginRequest req) {

        return User.find("email", req.email())
                .firstResultOptional()
                .map(userObj -> {
                    User user = (User) userObj;
                    if (!BCrypt.checkpw(req.password(), user.password)) {
                        return Response.status(401).entity("Sai mật khẩu").build();
                    }

                    String access = jwtService.generateAccessToken(user.email, Set.of("User"));
                    String refresh = jwtService.generateRefreshToken(user.email);

                    return Response.ok(new AuthResponse(access, refresh, 3600)).build();
                })
                .orElse(Response.status(401).entity("Không tìm thấy user").build());
    }

    @POST
    @Path("/refresh")
    @PermitAll
    @Operation(summary = "Refresh access token")
    public Response refresh(@Valid RefreshRequest req) {
        String email = jwtService.extractEmailFromRefreshToken(req.refreshToken());
        String access = jwtService.generateAccessToken(email, Set.of("User"));
        return Response.ok(new AuthResponse(access, req.refreshToken(), 3600)).build();
    }
}
