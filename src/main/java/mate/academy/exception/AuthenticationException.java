package mate.academy.exception;

public class AuthenticationException extends Exception {
    public AuthenticationException() {
        super("Error during authentication. Can't authenticate user");
    }
}
