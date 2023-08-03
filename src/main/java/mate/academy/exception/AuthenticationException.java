package mate.academy.exception;

public class AuthenticationException extends Exception {
    public AuthenticationException(String massage, Exception e) {
        super(massage);
    }
}
