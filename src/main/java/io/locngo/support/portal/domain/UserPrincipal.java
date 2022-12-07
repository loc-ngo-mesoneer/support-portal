package io.locngo.support.portal.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.locngo.support.portal.validation.ApiValidator;
import lombok.Getter;

@Getter
public class UserPrincipal implements UserDetails {

    private final User user;

    private UserPrincipal(final User user) {
        ApiValidator.requireNonNull(user, "user");
        
        this.user = user;
    }

    public static UserPrincipal of(final User user) {
        return new UserPrincipal(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(this.user.getRole())
                .map(Role::getAuthorities)
                .map(authorities -> authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.user.isBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }

}
