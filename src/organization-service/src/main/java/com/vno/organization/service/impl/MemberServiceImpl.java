package com.vno.organization.service.impl;

import com.vno.organization.exception.MemberAlreadyExistsException;
import com.vno.organization.exception.MemberNotFoundException;
import com.vno.organization.model.Member;
import com.vno.organization.model.MemberId;
import com.vno.organization.model.Organization;
import com.vno.organization.model.enums.MemberRole;
import com.vno.organization.model.enums.MemberStatus;
import com.vno.organization.repository.MemberRepository;
import com.vno.organization.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Member addMember(Organization organization, UUID userId, MemberRole role, MemberStatus status) {
        log.debug("Adding user {} to organization {} with role {}", userId, organization.getId(), role);
        
        // Check if member already exists
        if (memberRepository.existsByOrganizationIdAndUserId(organization.getId(), userId)) {
            throw new MemberAlreadyExistsException(organization.getId(), userId);
        }
        
        // Create new member
        Member member = new Member();
        member.setId(new MemberId(organization.getId(), userId));
        member.setOrganization(organization);
        member.setRole(role);
        member.setStatus(status);
        
        // Add to organization's member set
        organization.getMembers().add(member);
        
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public void removeMember(UUID organizationId, UUID userId) {
        log.debug("Removing user {} from organization {}", userId, organizationId);
        
        Member member = memberRepository.findByOrganizationIdAndUserId(organizationId, userId)
                .orElseThrow(() -> new MemberNotFoundException(organizationId, userId));
        
        // Check if this is the last owner
        if (member.getRole() == MemberRole.OWNER && 
            memberRepository.countByOrganizationIdAndRole(organizationId, MemberRole.OWNER) <= 1) {
            throw new IllegalStateException("Cannot remove the last owner of the organization");
        }
        
        memberRepository.delete(member);
    }

    @Override
    @Transactional
    public void updateMemberRole(UUID organizationId, UUID userId, MemberRole role) {
        log.debug("Updating role of user {} in organization {} to {}", userId, organizationId, role);
        
        Member member = memberRepository.findByOrganizationIdAndUserId(organizationId, userId)
                .orElseThrow(() -> new MemberNotFoundException(organizationId, userId));
        
        // Prevent changing the role of the last owner
        if (member.getRole() == MemberRole.OWNER && 
            memberRepository.countByOrganizationIdAndRole(organizationId, MemberRole.OWNER) <= 1) {
            throw new IllegalStateException("Cannot change the role of the last owner");
        }
        
        member.setRole(role);
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void updateMemberStatus(UUID organizationId, UUID userId, MemberStatus status) {
        log.debug("Updating status of user {} in organization {} to {}", userId, organizationId, status);
        
        Member member = memberRepository.findByOrganizationIdAndUserId(organizationId, userId)
                .orElseThrow(() -> new MemberNotFoundException(organizationId, userId));
        
        member.setStatus(status);
        memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserMember(UUID organizationId, UUID userId) {
        return memberRepository.existsByOrganizationIdAndUserId(organizationId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserOwner(UUID organizationId, UUID userId) {
        return memberRepository.existsByOrganizationIdAndUserIdAndRole(organizationId, userId, MemberRole.OWNER);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserAdmin(UUID organizationId, UUID userId) {
        return memberRepository.existsByOrganizationIdAndUserIdAndRole(organizationId, userId, MemberRole.ADMIN);
    }
}
