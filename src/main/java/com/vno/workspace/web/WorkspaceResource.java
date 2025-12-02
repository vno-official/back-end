package com.vno.workspace.web;

import com.vno.core.entity.User;
import com.vno.core.entity.Workspace;
import com.vno.core.tenant.TenantContext;
import io.quarkus.security.Authenticated;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/api/workspaces")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class WorkspaceResource {

    @GET
    public Response listWorkspaces() {
        Long orgId = TenantContext.getOrganizationId();
        if (orgId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"No organization context\"}")
                .build();
        }

        List<Workspace> workspaces = Workspace.list("organizationId = ?1 and deletedAt is null", orgId);
        return Response.ok(workspaces).build();
    }

    @POST
    @Transactional
    public Response createWorkspace(WorkspaceCreateRequest request, @Context SecurityContext securityContext) {
        Long orgId = TenantContext.getOrganizationId();
        if (orgId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"No organization context\"}")
                .build();
        }

        // Get user ID from JWT
        String userId = securityContext.getUserPrincipal().getName();
        User user = User.findById(Long.parseLong(userId));

        Workspace workspace = new Workspace();
        workspace.organizationId = orgId;
        workspace.name = request.name;
        workspace.iconEmoji = request.iconEmoji;
        workspace.createdBy = user;
        workspace.persist();

        return Response.status(Response.Status.CREATED).entity(workspace).build();
    }

    public static class WorkspaceCreateRequest {
        public String name;
        public String iconEmoji;
    }
}
