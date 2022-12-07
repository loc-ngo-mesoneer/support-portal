package io.locngo.support.portal.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.locngo.support.portal.controller.LoginRequest;
import io.locngo.support.portal.controller.RegisterRequest;
import io.locngo.support.portal.domain.Role;
import io.locngo.support.portal.domain.User;
import io.locngo.support.portal.exception.EmailAlreadyExistedException;
import io.locngo.support.portal.exception.UsernameAlreadyExistedException;
import io.locngo.support.portal.repository.UserRepository;
import io.locngo.support.portal.utility.JwtTokenProvider;
import io.locngo.support.portal.validation.ApiValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public User register(RegisterRequest request) {
        ApiValidator.requireNonNull(request, "request");

        this.validateUsernameAlreadyExisted(request.getUsername());
        this.validateEmailAlreadyExisted(request.getEmail());

        final String encryptedPassword = this.bCryptPasswordEncoder.encode(request.getPassword());

        final User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(encryptedPassword);
        user.setRole(Role.valueOf(request.getRole()));
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setProfileImageUrl(request.getProfileImageUrl());
        user.setJoinedDate(LocalDateTime.now());
        user.setActive(true);
        user.setBlocked(false);

        return this.userRepository.save(user);
    }

    private void validateUsernameAlreadyExisted(final String username) {
        this.userRepository.findByUsername(username)
            .ifPresent(entity -> {
                throw UsernameAlreadyExistedException.newInstance(entity.getUsername());
            });
    }

    private void validateEmailAlreadyExisted(final String email) {
        this.userRepository.findByEmail(email)
            .ifPresent(entity -> {
                throw EmailAlreadyExistedException.newInstance(entity.getEmail());
            });
    }

    @Override
    public String login(LoginRequest request) {
        ApiValidator.requireNonNull(request, "request");

        try {
            final Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), 
                    request.getPassword()
                )
            );

            return this.jwtTokenProvider.generateJwtToken(authentication);
        } catch(Exception exception) {
            log.error(exception.getLocalizedMessage());
            throw exception;
        }
    }
}
