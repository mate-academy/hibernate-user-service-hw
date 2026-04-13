package mate.academy.exception;

public class RegistrationException extends Exception {
    public RegistrationException(String msg) {
        super(msg);
    }

    public RegistrationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
