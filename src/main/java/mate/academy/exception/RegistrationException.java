package mate.academy.exception;

public class RegistrationException extends Exception {
    public RegistrationException(String text) {
        super(text);
    }

    public RegistrationException(String text, Exception e) {
        super(text, e);
    }
}
