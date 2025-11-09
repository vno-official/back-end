package com.vno.organization.model.enums;

public enum MemberRole {
    OWNER,      // Full access, can manage organization settings and members
    ADMIN,      // Can manage members and content
    EDITOR,     // Can create and edit content
    VIEWER,     // Can only view content
    GUEST       // Limited access, can be restricted from certain content
}
