package com.vno.core.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "invitations")
public class Invitation extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    public Organization organization;

    @Column(nullable = false)
    public String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Role role = Role.MEMBER;

    @Column(nullable = false, unique = true)
    public String token;

    @ManyToOne
    @JoinColumn(name = "invited_by")
    public User invitedBy;

    @Column(name = "expires_at", nullable = false)
    public Instant expiresAt;

    @Column(name = "accepted_at")
    public Instant acceptedAt;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt = Instant.now();

    // Helper methods
    public static Invitation createInvitation(Organization org, String email, Role role, User invitedBy, int daysValid) {
        Invitation invitation = new Invitation();
        invitation.organization = org;
        invitation.email = email;
        invitation.role = role;
        invitation.invitedBy = invitedBy;
        invitation.token = UUID.randomUUID().toString();
        invitation.expiresAt = Instant.now().plusSeconds(daysValid * 24 * 60 * 60);
        return invitation;
    }

    public static Invitation findByToken(String token) {
        return find("token = ?1 and acceptedAt is null and expiresAt > ?2", 
                    token, Instant.now()).firstResult();
    }

    public boolean isValid() {
        return acceptedAt == null && expiresAt.isAfter(Instant.now());
    }
}
