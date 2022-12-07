package io.locngo.support.portal.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class RegisterRequest {

    private String email;
    
    private String username;

    private String password;

    private String role;

    private String firstname;

    private String lastname;

    private String profileImageUrl;
}


