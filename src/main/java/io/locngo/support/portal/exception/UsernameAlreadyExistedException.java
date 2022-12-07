package io.locngo.support.portal.exception;

public class UsernameAlreadyExistedException extends RuntimeException {
    
    private UsernameAlreadyExistedException(final String message) {
        super(message);
    }

    public static UsernameAlreadyExistedException newInstance(final String username) {
        final String errorMessage = String.format(
            "User with username [%s] already existed!", 
            username
        );
        return new UsernameAlreadyExistedException(errorMessage);
    }
}
