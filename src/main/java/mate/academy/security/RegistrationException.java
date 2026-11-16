package mate.academy.security;

public class RegistrationException extends Exception {

    public RegistrationException(final String message) {
        super(message);
    }

    public RegistrationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
