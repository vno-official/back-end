package com.vno.organization.exception;

import java.util.UUID;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(UUID organizationId, UUID userId) {
        super(String.format("User %s is not a member of organization %s", userId, organizationId));
    }
    
    public MemberNotFoundException(String message) {
        super(message);
    }
}
