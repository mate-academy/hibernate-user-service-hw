package mate.academy.exception;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String text) {
        super(text);
    }

    public RegistrationException(String text, Exception e) {
        super(text, e);
    }
}
