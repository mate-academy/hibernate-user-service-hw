package mate.academy.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message, Exception e) {
        super(message, e);
    }

    public AuthenticationException(String messsage) {
        super(messsage);
    }
}
