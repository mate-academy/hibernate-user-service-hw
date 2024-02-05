package mate.academy.exception;

import java.io.IOException;

public class RegistrationException extends IOException {
    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
