package io.locngo.support.portal.service;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.locngo.support.portal.domain.User;
import io.locngo.support.portal.domain.UserPrincipal;
import io.locngo.support.portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    final String errorMessage = String.format(
                        "Cannot found user with username [%s]",
                        username
                    );
                    final UsernameNotFoundException exception = new UsernameNotFoundException(errorMessage);

                    log.error(errorMessage, exception);
                    throw exception;
                });
        
        user.setLastLoginDate(LocalDateTime.now());

        return UserPrincipal.of(user);
    }
    
}
