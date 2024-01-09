package mate.academy.exception;

public class RegistrationException extends Exception {
    public RegistrationException(String message, Throwable ex) {
        super(message, ex);
    }

    public RegistrationException(String message) {
        super(message);
    }
}
