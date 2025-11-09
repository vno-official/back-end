package com.vno.organization.exception;

import java.util.UUID;

public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException(UUID organizationId) {
        super("Organization not found with id: " + organizationId);
    }
    
    public OrganizationNotFoundException(String message) {
        super(message);
    }
}
