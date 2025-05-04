package mate.academy.exception;

public class RegistrationException extends Exception {
    public RegistrationException(String msg, Exception e) {
        super(msg, e);
    }

    public RegistrationException(String smg) {
        super(smg);
    }
}
