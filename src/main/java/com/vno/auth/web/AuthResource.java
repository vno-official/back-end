package com.vno.auth.web;

import com.vno.auth.dto.AuthResponse;
import com.vno.auth.dto.MagicLinkRequest;
import com.vno.auth.service.EmailService;
import com.vno.auth.service.JwtService;
import com.vno.auth.service.MagicLinkService;
import com.vno.core.entity.Membership;
import com.vno.core.entity.Organization;
import com.vno.core.entity.User;
import com.vno.org.service.OrgBootstrapService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    MagicLinkService magicLinkService;

    @Inject
    EmailService emailService;

    @Inject
    JwtService jwtService;

    @Inject
    OrgBootstrapService orgBootstrapService;

    @POST
    @Path("/magic-link")
    public Response requestMagicLink(@Valid MagicLinkRequest request) {
        String token = magicLinkService.generateToken(request.email);
        emailService.sendMagicLink(request.email, token);
        
        return Response.ok()
            .entity("{\"message\":\"Magic link sent to " + request.email + "\"}")
            .build();
    }

    @GET
    @Path("/callback")
    @Transactional
    public Response callback(@QueryParam("token") String token) {
        if (token == null || token.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Token is required\"}")
                .build();
        }

        String email = magicLinkService.validateToken(token);
        if (email == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\":\"Invalid or expired token\"}")
                .build();
        }

        // Find or create user
        User user = User.findByEmail(email);
        boolean isNewUser = (user == null);
        
        if (isNewUser) {
            user = new User();
            user.email = email;
            user.name = email.split("@")[0];
            user.persist();
        }

        // Find or create organization
        Membership membership = Membership.find("user.id = ?1", user.id).firstResult();
        Organization org;
        
        if (membership == null) {
            // New user - bootstrap organization
            org = orgBootstrapService.bootstrapOrganization(user);
        } else {
            org = membership.organization;
        }

        // Generate JWT
        String jwtToken = jwtService.generateToken(
            user.id,
            user.email,
            user.name,
            org.id,
            membership != null ? membership.role.name() : "OWNER"
        );

        // Return auth response
        AuthResponse response = AuthResponse.create(jwtToken, user, org);
        return Response.ok(response).build();
    }
}
