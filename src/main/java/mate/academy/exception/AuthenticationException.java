package mate.academy.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(String message) {
    }
}
