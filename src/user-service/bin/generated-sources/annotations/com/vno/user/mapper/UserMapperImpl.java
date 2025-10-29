package com.vno.user.mapper;

import com.vno.user.dto.UserRequest;
import com.vno.user.dto.UserResponse;
import com.vno.user.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-27T01:22:21+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.avatarUrl( userRequest.getAvatarUrl() );
        user.email( userRequest.getEmail() );
        user.firstName( userRequest.getFirstName() );
        user.lastName( userRequest.getLastName() );
        user.locale( userRequest.getLocale() );
        user.password( userRequest.getPassword() );
        user.phoneNumber( userRequest.getPhoneNumber() );
        user.status( userRequest.getStatus() );
        user.timezone( userRequest.getTimezone() );
        user.username( userRequest.getUsername() );

        return user.build();
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.avatarUrl( user.getAvatarUrl() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.email( user.getEmail() );
        userResponse.emailVerified( user.isEmailVerified() );
        userResponse.firstName( user.getFirstName() );
        userResponse.id( user.getId() );
        userResponse.lastLogin( user.getLastLogin() );
        userResponse.lastName( user.getLastName() );
        userResponse.locale( user.getLocale() );
        userResponse.phoneNumber( user.getPhoneNumber() );
        userResponse.status( user.getStatus() );
        userResponse.timezone( user.getTimezone() );
        userResponse.updatedAt( user.getUpdatedAt() );
        userResponse.username( user.getUsername() );

        return userResponse.build();
    }

    @Override
    public void updateEntity(UserRequest userRequest, User user) {
        if ( userRequest == null ) {
            return;
        }

        if ( userRequest.getAvatarUrl() != null ) {
            user.setAvatarUrl( userRequest.getAvatarUrl() );
        }
        if ( userRequest.getEmail() != null ) {
            user.setEmail( userRequest.getEmail() );
        }
        if ( userRequest.getFirstName() != null ) {
            user.setFirstName( userRequest.getFirstName() );
        }
        if ( userRequest.getLastName() != null ) {
            user.setLastName( userRequest.getLastName() );
        }
        if ( userRequest.getLocale() != null ) {
            user.setLocale( userRequest.getLocale() );
        }
        if ( userRequest.getPassword() != null ) {
            user.setPassword( userRequest.getPassword() );
        }
        if ( userRequest.getPhoneNumber() != null ) {
            user.setPhoneNumber( userRequest.getPhoneNumber() );
        }
        if ( userRequest.getStatus() != null ) {
            user.setStatus( userRequest.getStatus() );
        }
        if ( userRequest.getTimezone() != null ) {
            user.setTimezone( userRequest.getTimezone() );
        }
        if ( userRequest.getUsername() != null ) {
            user.setUsername( userRequest.getUsername() );
        }
    }
}
