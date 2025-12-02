package com.vno.workspace.web;

import com.vno.core.entity.Page;
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

@Path("/api/pages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class PageResource {

    @GET
    public Response listPages(@QueryParam("workspaceId") Long workspaceId) {
        Long orgId = TenantContext.getOrganizationId();
        if (orgId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"No organization context\"}")
                .build();
        }

        List<Page> pages;
        if (workspaceId != null) {
            pages = Page.list("organizationId = ?1 and workspace.id = ?2 and deletedAt is null", orgId, workspaceId);
        } else {
            pages = Page.list("organizationId = ?1 and deletedAt is null", orgId);
        }
        
        return Response.ok(pages).build();
    }

    @POST
    @Transactional
    public Response createPage(PageCreateRequest request, @Context SecurityContext securityContext) {
        Long orgId = TenantContext.getOrganizationId();
        if (orgId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"No organization context\"}")
                .build();
        }

        // Get user ID from JWT
        String userId = securityContext.getUserPrincipal().getName();
        User user = User.findById(Long.parseLong(userId));

        // Verify workspace belongs to org
        Workspace workspace = Workspace.findByIdAndOrg(request.workspaceId, orgId);
        if (workspace == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Workspace not found\"}")
                .build();
        }

        Page page = new Page();
        page.organizationId = orgId;
        page.workspace = workspace;
        page.title = request.title != null ? request.title : "Untitled";
        page.iconEmoji = request.iconEmoji;
        page.createdBy = user;
        
        if (request.parentPageId != null) {
            Page parentPage = Page.findByIdAndOrg(request.parentPageId, orgId);
            if (parentPage != null) {
                page.parentPage = parentPage;
            }
        }
        
        page.persist();
        return Response.status(Response.Status.CREATED).entity(page).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePage(@PathParam("id") Long id) {
        Long orgId = TenantContext.getOrganizationId();
        if (orgId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"No organization context\"}")
                .build();
        }

        Page page = Page.findByIdAndOrg(id, orgId);
        if (page == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Page not found\"}")
                .build();
        }

        page.softDelete();
        page.persist();
        
        return Response.ok("{\"message\":\"Page deleted\"}").build();
    }

    public static class PageCreateRequest {
        public Long workspaceId;
        public String title;
        public String iconEmoji;
        public Long parentPageId;
    }
}
