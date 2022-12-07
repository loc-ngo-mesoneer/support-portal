package io.locngo.support.portal.constant;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SecurityConstant {

    public static final long EXPIRATION_TIME_IN_MILLISECONDS = 432_000_000;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String JWT_TOKEN_HEADER = "X-Jwt-Token";

    public static final String TOKEN_CANNOT_BE_VERIFIED_MESSAGE = "Token cannot be verified";

    public static final String ISSUER = "Loc Ngo";

    public static final String AUDIENCE = "User Management Portal";

    public static final String AUTHORITIES = "authorities";

    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized, you need to login";

    public static final String FOBBIDEN_MESSAGE = "You do not have permission";

    private static final String API_VERSION_REGEX = "/(api)/(v\\d)/";

    public static final RequestMatcher[] PUBLIC_URLS = {
            RegexRequestMatcher.regexMatcher(API_VERSION_REGEX + "auth/register.*"),
            RegexRequestMatcher.regexMatcher(API_VERSION_REGEX + "auth/login.*")
    };
}
