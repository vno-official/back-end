package com.vno.organization.service;

import com.vno.organization.model.Member;
import com.vno.organization.model.Organization;
import com.vno.organization.model.enums.MemberRole;
import com.vno.organization.model.enums.MemberStatus;

import java.util.UUID;

public interface MemberService {
    
    Member addMember(Organization organization, UUID userId, MemberRole role, MemberStatus status);
    
    void removeMember(UUID organizationId, UUID userId);
    
    void updateMemberRole(UUID organizationId, UUID userId, MemberRole role);
    
    void updateMemberStatus(UUID organizationId, UUID userId, MemberStatus status);
    
    boolean isUserMember(UUID organizationId, UUID userId);
    
    boolean isUserOwner(UUID organizationId, UUID userId);
    
    boolean isUserAdmin(UUID organizationId, UUID userId);
}
