package io.locngo.support.portal.service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.locngo.support.portal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginAttemptListener {
    
    private final UserRepository userRepository;

    private final LoadingCache<String, AtomicInteger> loginAttemptCache;

    private final int loginMaxAttempts;

    public LoginAttemptListener(
        UserRepository userRepository,
        @Value("${login.maxAttempts:3}") int loginMaxAttempts
    ) {
        this.loginMaxAttempts = loginMaxAttempts;

        this.userRepository = userRepository;
        this.loginAttemptCache = CacheBuilder.newBuilder()
            .maximumSize(300)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(new CacheLoader<String, AtomicInteger>() {
                @Override
                public AtomicInteger load(String key) throws Exception {
                    return new AtomicInteger(0);
                }
            });
    }

    @EventListener
    public void onAuthenticationSuccess(final AuthenticationSuccessEvent event) {
        final String username = this.extractUsernameFromAuthenticationEvent(event);

        if (StringUtils.isBlank(username)) {
            log.warn("Cannot handle onAuthenticationSuccess because username is blank");
            return;
        }

        this.loginAttemptCache.invalidate(username);
    }

    private String extractUsernameFromAuthenticationEvent(AbstractAuthenticationEvent event) {
        return Optional.ofNullable(event)
            .map(AbstractAuthenticationEvent::getAuthentication)
            .map(Authentication::getName)
            .orElse(StringUtils.EMPTY);
    }

    @Transactional
    @EventListener
    public void onAuthenticationFailure(final AuthenticationFailureBadCredentialsEvent event) throws ExecutionException {
        final String username = this.extractUsernameFromAuthenticationEvent(event);

        if (StringUtils.isBlank(username)) {
            log.warn("Cannot handle onAuthenticationFailure because username is blank");
            return;
        }

        final int attempts = this.loginAttemptCache.get(username).incrementAndGet();

        log.warn("Login with username [{}] failed, attempts: {}", username, attempts);

        if(attempts > this.loginMaxAttempts) {
            this.userRepository.findByUsername(username)
                .filter(entity -> !entity.isBlocked())
                .map(entity -> {
                    entity.setBlocked(true);
                    return entity;
                }).ifPresent(entity -> {
                    log.warn(
                        "User [{}] was blocked because exceeded max attempts login failed [{}]", 
                        username, 
                        this.loginMaxAttempts
                    );
                    this.userRepository.save(entity);
                });

            this.loginAttemptCache.invalidate(username);
        }
    }
}
