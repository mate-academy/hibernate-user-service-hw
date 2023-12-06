package mate.academy.exception;

public class AuthenticationException extends Exception {
    public AuthenticationException(String message, Exception e) {
        super(message, e);
    }

    public AuthenticationException(String messsage) {
        super(messsage);
    }
}
