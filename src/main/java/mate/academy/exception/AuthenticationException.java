package mate.academy.exception;

public class AuthenticationException extends Exception {
    public AuthenticationException(String msg, Exception e) {
        super(msg, e);
    }

    public AuthenticationException(String msg) {
        super(msg);
    }
}
