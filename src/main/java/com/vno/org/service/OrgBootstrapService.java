package com.vno.org.service;

import com.vno.core.entity.Membership;
import com.vno.core.entity.Organization;
import com.vno.core.entity.Role;
import com.vno.core.entity.User;
import com.vno.core.entity.Workspace;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.Random;

@ApplicationScoped
public class OrgBootstrapService {

    private static final Random RANDOM = new Random();

    @Transactional
    public Organization bootstrapOrganization(User user) {
        // Generate org slug from email domain
        String slug = generateSlugFromEmail(user.email);
        
        // Ensure slug is unique
        slug = ensureUniqueSlug(slug);
        
        // Create organization
        Organization org = new Organization();
        org.name = slug.substring(0, 1).toUpperCase() + slug.substring(1);
        org.slug = slug;
        org.persist();
        
        // Create OWNER membership
        Membership membership = new Membership();
        membership.user = user;
        membership.organization = org;
        membership.role = Role.OWNER;
        membership.persist();
        
        // Create default workspace
        Workspace workspace = new Workspace();
        workspace.organizationId = org.id;
        workspace.name = "General";
        workspace.createdBy = user;
        workspace.persist();
        
        return org;
    }

    private String generateSlugFromEmail(String email) {
        // Extract domain from email (e.g., user@acme.com -> acme)
        int atIndex = email.indexOf('@');
        if (atIndex > 0) {
            String domain = email.substring(atIndex + 1);
            int dotIndex = domain.indexOf('.');
            if (dotIndex > 0) {
                return domain.substring(0, dotIndex).toLowerCase();
            }
            return domain.toLowerCase();
        }
        // Fallback: use part before @
        return email.substring(0, atIndex > 0 ? atIndex : email.length()).toLowerCase();
    }

    private String ensureUniqueSlug(String baseSlug) {
        String slug = baseSlug;
        int attempts = 0;
        while (Organization.slugExists(slug) && attempts < 10) {
            slug = baseSlug + RANDOM.nextInt(1000);
            attempts++;
        }
        return slug;
    }
}
