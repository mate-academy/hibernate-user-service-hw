package mate.academy.exception;

public class RegistrationFailureException extends RuntimeException {

    public RegistrationFailureException() {
        super();
    }

    public RegistrationFailureException(String message) {
        super(message);
    }

    public RegistrationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
