package mate.academy.exception;

public class AuthenticationException extends Exception {
    public AuthenticationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
