package com.vno.organization.mapper;

import com.vno.organization.dto.MemberResponse;
import com.vno.organization.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);
    
    @Mapping(source = "id.userId", target = "userId")
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(target = "username", expression = "java(getUserUsername(member))")
    @Mapping(target = "email", expression = "java(getUserEmail(member))")
    @Mapping(target = "firstName", expression = "java(getUserFirstName(member))")
    @Mapping(target = "lastName", expression = "java(getUserLastName(member))")
    @Mapping(target = "avatarUrl", expression = "java(getUserAvatarUrl(member))")
    @Mapping(source = "createdAt", target = "joinedAt")
    MemberResponse toResponse(Member member);
    
    // These methods will be implemented to fetch user details from the user service
    @Named("getUserUsername")
    default String getUserUsername(Member member) {
        // TODO: Fetch username from user service using member.getId().getUserId()
        return "username";
    }
    
    @Named("getUserEmail")
    default String getUserEmail(Member member) {
        // TODO: Fetch email from user service using member.getId().getUserId()
        return "user@example.com";
    }
    
    @Named("getUserFirstName")
    default String getUserFirstName(Member member) {
        // TODO: Fetch first name from user service using member.getId().getUserId()
        return "First";
    }
    
    @Named("getUserLastName")
    default String getUserLastName(Member member) {
        // TODO: Fetch last name from user service using member.getId().getUserId()
        return "Last";
    }
    
    @Named("getUserAvatarUrl")
    default String getUserAvatarUrl(Member member) {
        // TODO: Fetch avatar URL from user service using member.getId().getUserId()
        return "https://example.com/avatar/default.png";
    }
}
