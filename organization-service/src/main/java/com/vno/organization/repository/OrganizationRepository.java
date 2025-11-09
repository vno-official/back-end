package com.vno.organization.repository;

import com.vno.organization.model.Organization;
import com.vno.organization.model.enums.MemberRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID>, JpaSpecificationExecutor<Organization> {
    
    @Query("SELECT DISTINCT o FROM Organization o LEFT JOIN o.members m WHERE o.createdBy = :userId OR (m.id.userId = :userId AND m.status = 'ACTIVE')")
    Page<Organization> findAllByUserMembership(@Param("userId") UUID userId, Pageable pageable);
    
    boolean existsByName(String name);
    
    @Query("SELECT COUNT(o) > 0 FROM Organization o JOIN o.members m WHERE o.id = :organizationId AND m.id.userId = :userId AND m.status = 'ACTIVE'")
    boolean isUserMember(@Param("organizationId") UUID organizationId, @Param("userId") UUID userId);
    
    @Query("SELECT COUNT(o) > 0 FROM Organization o JOIN o.members m WHERE o.id = :organizationId AND m.id.userId = :userId AND m.role = 'OWNER'")
    boolean isUserOwner(@Param("organizationId") UUID organizationId, @Param("userId") UUID userId);
    
    @Query("SELECT COUNT(o) > 0 FROM Organization o JOIN o.members m WHERE o.id = :organizationId AND m.id.userId = :userId AND m.role = :role")
    boolean existsByIdAndMembers_UserIdAndRole(@Param("organizationId") UUID organizationId, @Param("userId") UUID userId, @Param("role") MemberRole role);
}
