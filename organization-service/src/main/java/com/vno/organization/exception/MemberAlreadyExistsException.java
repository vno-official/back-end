package com.vno.organization.exception;

import java.util.UUID;

public class MemberAlreadyExistsException extends RuntimeException {
    public MemberAlreadyExistsException(UUID organizationId, UUID userId) {
        super(String.format("User %s is already a member of organization %s", userId, organizationId));
    }
    
    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
