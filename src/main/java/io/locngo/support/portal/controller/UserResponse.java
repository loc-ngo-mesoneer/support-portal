package io.locngo.support.portal.controller;

import java.time.LocalDateTime;

import io.locngo.support.portal.domain.User;
import io.locngo.support.portal.validation.ApiValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class UserResponse {

    private final String id;

    private final String email;
    
    private final String username;

    private final String role;

    private final String firstname;

    private final String lastname;

    private final String profileImageUrl;

    private final LocalDateTime lastLoginDate;

    private final LocalDateTime joinedDate;

    private final boolean active;

    private final boolean blocked;

    public static UserResponse fromUser(final User user) {
        ApiValidator.requireNonNull(user, "user");

        return UserResponse.of(
            user.getUserId(),
            user.getEmail(), 
            user.getUsername(),
            user.getRole().name(), 
            user.getFirstname(), 
            user.getLastname(), 
            user.getProfileImageUrl(), 
            user.getLastLoginDate(),
            user.getJoinedDate(), 
            user.isActive(), 
            user.isBlocked()
        );
    }
}
