package com.vno.core.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "memberships", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "organization_id"})
})
public class Membership extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    public Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Role role;

    @Column(name = "joined_at", nullable = false)
    public Instant joinedAt = Instant.now();

    // Repository methods
    public static Membership findByUserAndOrg(Long userId, Long orgId) {
        return find("user.id = ?1 and organization.id = ?2", userId, orgId).firstResult();
    }

    public static boolean isMember(Long userId, Long orgId) {
        return count("user.id = ?1 and organization.id = ?2", userId, orgId) > 0;
    }

    public static Role getUserRole(Long userId, Long orgId) {
        Membership membership = findByUserAndOrg(userId, orgId);
        return membership != null ? membership.role : null;
    }
}
