package com.vno.organization.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.vno.organization.model.enums.MemberRole;
import com.vno.organization.model.enums.MemberStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class Member {
    
    @EmbeddedId
    private MemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("organizationId")
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Helper methods
    public void setOrganization(Organization organization) {
        this.organization = organization;
        if (id == null) {
            id = new MemberId();
        }
        id.setOrganizationId(organization.getId());
    }
}
