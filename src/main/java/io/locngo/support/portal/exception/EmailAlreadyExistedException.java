package io.locngo.support.portal.exception;

public class EmailAlreadyExistedException extends RuntimeException {
    
    private EmailAlreadyExistedException(final String message) {
        super(message);
    }

    public static EmailAlreadyExistedException newInstance(final String email) {
        final String errorMessage = String.format(
            "User with email %s already existed!", 
            email
        );
        return new EmailAlreadyExistedException(errorMessage);
    }
}
