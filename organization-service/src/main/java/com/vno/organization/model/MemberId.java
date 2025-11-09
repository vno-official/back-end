package com.vno.organization.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MemberId implements Serializable {
    
    @Column(name = "organization_id")
    private UUID organizationId;
    
    @Column(name = "user_id")
    private UUID userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberId memberId = (MemberId) o;
        return Objects.equals(organizationId, memberId.organizationId) &&
               Objects.equals(userId, memberId.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, userId);
    }
}
