package com.vno.organization.service.impl;

import com.vno.organization.dto.OrganizationRequest;
import com.vno.organization.dto.OrganizationResponse;
import com.vno.organization.exception.OrganizationNotFoundException;
import com.vno.organization.exception.UnauthorizedAccessException;
import com.vno.organization.mapper.OrganizationMapper;
import com.vno.organization.model.Organization;
import com.vno.organization.model.enums.MemberRole;
import com.vno.organization.model.enums.MemberStatus;
import com.vno.organization.model.enums.OrganizationStatus;
import com.vno.organization.repository.OrganizationRepository;
import com.vno.organization.service.MemberService;
import com.vno.organization.service.OrganizationService;
import com.vno.organization.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final UserServiceClient userServiceClient;
    private final MemberService memberService;

    @Override
    @Transactional
    public OrganizationResponse createOrganization(OrganizationRequest request, UUID createdBy) {
        log.info("Creating organization: {}", request.getName());
        
        if (organizationRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Organization with this name already exists");
        }

        Organization organization = organizationMapper.toEntity(request);
        organization.setCreatedBy(createdBy);
        organization.setStatus(OrganizationStatus.ACTIVE);
        
        Organization savedOrganization = organizationRepository.save(organization);
        
        // Add the creator as an owner
        memberService.addMember(savedOrganization, createdBy, MemberRole.OWNER, MemberStatus.ACTIVE);
        
        log.info("Created organization with id: {}", savedOrganization.getId());
        return organizationMapper.toResponse(savedOrganization);
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationResponse getOrganizationById(UUID id, UUID userId) {
        log.debug("Fetching organization with id: {}", id);
        
        Organization organization = getOrganizationEntity(id);
        
        // Check if user has access to this organization
        if (!isUserMember(id, userId) && !isUserOwner(id, userId)) {
            throw new UnauthorizedAccessException("You don't have permission to access this organization");
        }
        
        return organizationMapper.toResponse(organization);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationResponse> getAllOrganizations(Pageable pageable, UUID userId) {
        log.debug("Fetching all organizations for user: {}", userId);
        return organizationRepository.findAllByUserMembership(userId, pageable)
                .map(organizationMapper::toResponse);
    }

    @Override
    @Transactional
    public OrganizationResponse updateOrganization(UUID id, OrganizationRequest request, UUID userId) {
        log.info("Updating organization with id: {}", id);
        
        Organization organization = getOrganizationEntity(id);
        
        // Only owners can update organization
        if (!isUserOwner(id, userId)) {
            throw new UnauthorizedAccessException("Only organization owners can update organization details");
        }
        
        // Check if name is being changed and if it's already taken
        if (!organization.getName().equals(request.getName()) && 
            organizationRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Organization with this name already exists");
        }
        
        organizationMapper.updateEntity(request, organization);
        Organization updatedOrganization = organizationRepository.save(organization);
        
        log.info("Updated organization with id: {}", id);
        return organizationMapper.toResponse(updatedOrganization);
    }

    @Override
    @Transactional
    public void deleteOrganization(UUID id, UUID userId) {
        log.info("Deleting organization with id: {}", id);
        
        Organization organization = getOrganizationEntity(id);
        
        // Only owners can delete organization
        if (!isUserOwner(id, userId)) {
            throw new UnauthorizedAccessException("Only organization owners can delete the organization");
        }
        
        organizationRepository.delete(organization);
        log.info("Deleted organization with id: {}", id);
    }

    @Override
    @Transactional
    public OrganizationResponse addMember(UUID organizationId, UUID memberId, UUID userId) {
        log.info("Adding member {} to organization {}", memberId, organizationId);
        
        // Only owners or admins can add members
        if (!isUserOwner(organizationId, userId) && !isUserAdmin(organizationId, userId)) {
            throw new UnauthorizedAccessException("You don't have permission to add members");
        }
        
        Organization organization = getOrganizationEntity(organizationId);
        
        // Check if user exists (call user service)
        if (!userServiceClient.userExists(memberId)) {
            throw new IllegalArgumentException("User not found");
        }
        
        // Add member with default role (can be customized)
        memberService.addMember(organization, memberId, MemberRole.VIEWER, MemberStatus.PENDING);
        
        log.info("Added member {} to organization {}", memberId, organizationId);
        return organizationMapper.toResponse(organization);
    }

    @Override
    @Transactional
    public void removeMember(UUID organizationId, UUID memberId, UUID userId) {
        log.info("Removing member {} from organization {}", memberId, organizationId);
        
        // Users can remove themselves, owners can remove anyone, admins can remove non-admins
        if (!userId.equals(memberId) && !isUserOwner(organizationId, userId) && 
            !(isUserAdmin(organizationId, userId) && !isUserAdmin(organizationId, memberId))) {
            throw new UnauthorizedAccessException("You don't have permission to remove this member");
        }
        
        memberService.removeMember(organizationId, memberId);
        log.info("Removed member {} from organization {}", memberId, organizationId);
    }

    @Override
    @Transactional
    public OrganizationResponse updateMemberRole(UUID organizationId, UUID memberId, String role, UUID userId) {
        log.info("Updating role of member {} in organization {} to {}", memberId, organizationId, role);
        
        // Only owners can update roles
        if (!isUserOwner(organizationId, userId)) {
            throw new UnauthorizedAccessException("Only organization owners can update member roles");
        }
        
        // Cannot change owner's role
        if (isUserOwner(organizationId, memberId)) {
            throw new UnauthorizedAccessException("Cannot change the role of an organization owner");
        }
        
        MemberRole memberRole = MemberRole.valueOf(role.toUpperCase());
        memberService.updateMemberRole(organizationId, memberId, memberRole);
        
        log.info("Updated role of member {} in organization {} to {}", memberId, organizationId, role);
        return getOrganizationById(organizationId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationResponse> searchOrganizations(Specification<Organization> spec, Pageable pageable, UUID userId) {
        log.debug("Searching organizations with specification");
        // Add additional filtering for user access if needed
        return organizationRepository.findAll(spec, pageable)
                .map(organizationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Organization getOrganizationEntity(UUID id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));
    }

    @Override
    public boolean isUserMember(UUID organizationId, UUID userId) {
        return organizationRepository.isUserMember(organizationId, userId);
    }

    @Override
    public boolean isUserOwner(UUID organizationId, UUID userId) {
        return organizationRepository.isUserOwner(organizationId, userId);
    }
    
    private boolean isUserAdmin(UUID organizationId, UUID userId) {
        // Implement logic to check if user is admin in the organization
        // This is a simplified version - you might want to add this to the repository
        return organizationRepository.existsByIdAndMembers_UserIdAndRole(
            organizationId, userId, MemberRole.ADMIN);
    }
}
