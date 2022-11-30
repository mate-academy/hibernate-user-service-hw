package mate.academy.exception;

public class RegistrationException extends RuntimeException {
    public RegistrationException(Throwable e) {
        super("Error during registration. Can't register user", e);
    }
}
