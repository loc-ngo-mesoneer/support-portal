package io.locngo.support.portal.domain;

import java.util.Collection;
import java.util.List;

public enum Role {
    ROLE_USER {
        @Override
        public Collection<String> getAuthorities() {
            return List.of(
                "user:read"
            );
        }
    },
    ROLE_HR {
        @Override
        public Collection<String> getAuthorities() {
            return List.of(
                "user:read",
                "user:update"
            );
        }
    },
    ROLE_MANAGER {
        @Override
        public Collection<String> getAuthorities() {
            return List.of(
                "user:read", 
                "user:update"
            );
        }
    },
    ROLE_ADMIN {
        @Override
        public Collection<String> getAuthorities() {
            return List.of(
                "user:read",
                "user:update",
                "user:create"
            );
        }
    },
    ROLE_SUPER_ADMIN {
        @Override
        public Collection<String> getAuthorities() {
            return List.of(
                "user:read",
                "user:update",
                "user:create",
                "user:delete"
            );
        }
    };

    public abstract Collection<String> getAuthorities();
}
