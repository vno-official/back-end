package com.vno.core.tenant;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@MappedSuperclass
@FilterDef(name = "organizationFilter", parameters = @ParamDef(name = "organizationId", type = Long.class))
@Filter(name = "organizationFilter", condition = "organization_id = :organizationId")
public abstract class TenantEntity extends PanacheEntity {

    @Column(name = "organization_id", nullable = false)
    public Long organizationId;

    @PrePersist
    public void setOrganizationIdFromContext() {
        if (this.organizationId == null) {
            this.organizationId = TenantContext.getOrganizationId();
        }
    }
}
