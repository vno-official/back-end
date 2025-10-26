package com.vno.organization.dto;

import com.vno.organization.model.enums.MemberRole;
import com.vno.organization.model.enums.MemberStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class MemberRequest {
    @NotNull(message = "User ID is required")
    private UUID userId;
    
    @NotNull(message = "Member role is required")
    private MemberRole role;
    
    private MemberStatus status = MemberStatus.PENDING;
}
