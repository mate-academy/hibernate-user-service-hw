package mate.academy.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Error during authentication. Can't authenticate user");
    }
}
