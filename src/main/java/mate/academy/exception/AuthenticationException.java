package mate.academy.exception;

public class AuthenticationException extends SecurityException {
    public AuthenticationException(String message) {
        super(message);
    }
}
