package mate.academy.exception;

public class AuthenticationException extends Exception {
    public AuthenticationException(String message, Throwable ex) {
        super(message, ex);
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
