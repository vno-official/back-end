package com.vno.organization.dto;

import com.vno.organization.model.enums.MemberRole;
import com.vno.organization.model.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private UUID userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private MemberRole role;
    private MemberStatus status;
    private LocalDateTime joinedAt;
    private LocalDateTime updatedAt;
}
