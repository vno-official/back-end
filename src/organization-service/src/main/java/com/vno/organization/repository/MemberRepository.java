package com.vno.organization.repository;

import com.vno.organization.model.Member;
import com.vno.organization.model.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberId> {
    
    @Query("SELECT m FROM Member m WHERE m.id.organizationId = :organizationId")
    List<Member> findAllByOrganizationId(@Param("organizationId") UUID organizationId);
    
    @Query("SELECT m FROM Member m WHERE m.id.userId = :userId")
    List<Member> findAllByUserId(@Param("userId") UUID userId);
    
    @Query("SELECT m FROM Member m WHERE m.id.organizationId = :organizationId AND m.id.userId = :userId")
    Optional<Member> findByOrganizationIdAndUserId(
        @Param("organizationId") UUID organizationId, 
        @Param("userId") UUID userId
    );
    
    @Query("SELECT COUNT(m) > 0 FROM Member m WHERE m.id.organizationId = :organizationId AND m.id.userId = :userId")
    boolean existsByOrganizationIdAndUserId(
        @Param("organizationId") UUID organizationId, 
        @Param("userId") UUID userId
    );
    
    @Query("SELECT COUNT(m) > 0 FROM Member m WHERE m.id.organizationId = :organizationId AND m.role = 'OWNER'")
    boolean existsOwnerInOrganization(@Param("organizationId") UUID organizationId);
}
