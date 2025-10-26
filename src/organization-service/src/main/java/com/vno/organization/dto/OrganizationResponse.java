package com.vno.organization.dto;

import com.vno.organization.model.enums.OrganizationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponse {
    private UUID id;
    private String name;
    private String description;
    private String logoUrl;
    private String websiteUrl;
    private OrganizationStatus status;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<MemberResponse> members;
}
