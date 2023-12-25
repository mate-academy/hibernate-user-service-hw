package mate.academy.exception;

import java.io.IOException;

public class RegistrationException extends IOException {
    public RegistrationException(String message, DataProcessingException e) {
        super(message);
    }
}
