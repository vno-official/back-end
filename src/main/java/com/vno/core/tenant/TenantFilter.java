package com.vno.core.tenant;

import com.vno.core.entity.Organization;
import com.vno.util.SubdomainUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class TenantFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Inject
    SubdomainUtil subdomainUtil;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        
        // Skip tenant resolution for auth endpoints and health checks
        if (path.startsWith("auth/") || path.startsWith("q/") || path.startsWith("api/health")) {
            return;
        }

        String host = requestContext.getHeaderString("Host");
        String orgSlug = subdomainUtil.extractOrgSlug(host);
        
        if (orgSlug != null) {
            Organization org = Organization.findBySlug(orgSlug);
            if (org != null) {
                TenantContext.setOrganizationId(org.id);
            } else {
                // Subdomain exists but no organization found
                requestContext.abortWith(
                    Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\":\"Organization not found\"}")
                        .build()
                );
            }
        }
        // If no subdomain, TenantContext remains null (for root domain requests)
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Clear tenant context after request completes
        TenantContext.clear();
    }
}
