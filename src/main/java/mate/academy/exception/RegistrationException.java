package mate.academy.exception;

public class RegistrationException extends Exception {
    public RegistrationException(String message, Throwable e) {
        super(message, e);
    }

    public RegistrationException(String message) {
        super(message);
    }
}
