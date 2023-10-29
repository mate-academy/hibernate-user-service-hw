package mate.academy.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String massage) {
        super(massage);
    }
}
