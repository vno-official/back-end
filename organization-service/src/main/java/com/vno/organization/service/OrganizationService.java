package com.vno.organization.service;

import com.vno.organization.dto.OrganizationRequest;
import com.vno.organization.dto.OrganizationResponse;
import com.vno.organization.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface OrganizationService {
    
    OrganizationResponse createOrganization(OrganizationRequest organizationRequest, UUID createdBy);
    
    OrganizationResponse getOrganizationById(UUID id, UUID userId);
    
    Page<OrganizationResponse> getAllOrganizations(Pageable pageable, UUID userId);
    
    OrganizationResponse updateOrganization(UUID id, OrganizationRequest organizationRequest, UUID userId);
    
    void deleteOrganization(UUID id, UUID userId);
    
    OrganizationResponse addMember(UUID organizationId, UUID memberId, UUID userId);
    
    void removeMember(UUID organizationId, UUID memberId, UUID userId);
    
    OrganizationResponse updateMemberRole(UUID organizationId, UUID memberId, String role, UUID userId);
    
    Page<OrganizationResponse> searchOrganizations(Specification<Organization> spec, Pageable pageable, UUID userId);
    
    Organization getOrganizationEntity(UUID id);
    
    boolean isUserMember(UUID organizationId, UUID userId);
    
    boolean isUserOwner(UUID organizationId, UUID userId);
}
