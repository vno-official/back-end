package com.vno.organization.dto;

import com.vno.organization.model.enums.OrganizationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrganizationRequest {
    @NotBlank(message = "Organization name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    private String logoUrl;
    private String websiteUrl;
    private OrganizationStatus status;
}
