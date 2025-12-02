package com.vno.core.entity;

import com.vno.core.tenant.TenantEntity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "workspaces")
public class Workspace extends TenantEntity {

    @Column(nullable = false)
    public String name;

    @Column(name = "icon_emoji")
    public String iconEmoji;

    @Column(name = "cover_url")
    public String coverUrl;

    @Column(name = "default_permission")
    public String defaultPermission = "organization";

    @ManyToOne
    @JoinColumn(name = "created_by")
    public User createdBy;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt = Instant.now();

    @Column(name = "deleted_at")
    public Instant deletedAt;

    // Repository methods
    public static Workspace findByIdAndOrg(Long id, Long orgId) {
        return find("id = ?1 and organizationId = ?2 and deletedAt is null", id, orgId).firstResult();
    }
}
