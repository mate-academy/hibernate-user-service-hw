package mate.academy.exception;

public class RegistrationValidationException extends Exception {
    public RegistrationValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationValidationException(String message) {
        super(message);
    }
}
