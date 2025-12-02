package com.vno.org.web;

import com.vno.core.entity.Organization;
import com.vno.core.tenant.TenantContext;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/org")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class OrgResource {

    @GET
    public Response getCurrentOrg() {
        Long orgId = TenantContext.getOrganizationId();
        if (orgId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"No organization context\"}")
                .build();
        }

        Organization org = Organization.findById(orgId);
        if (org == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Organization not found\"}")
                .build();
        }

        return Response.ok(org).build();
    }
}
