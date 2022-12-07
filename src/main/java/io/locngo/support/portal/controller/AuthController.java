package io.locngo.support.portal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.locngo.support.portal.domain.User;
import io.locngo.support.portal.service.IUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    
    private final IUserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<UserResponse> register(
        @RequestBody final RegisterRequest request
    ) { 
        final User registeredUser = this.userService.register(request);

        return new ResponseEntity<>(
            UserResponse.fromUser(registeredUser),
            HttpStatus.OK
        );
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest request) {
        
        final String jwtToken = this.userService.login(request);

        return ResponseEntity.ok()
                .body(LoginResponse.of(jwtToken));
    }
}
