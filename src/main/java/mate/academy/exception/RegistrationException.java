package mate.academy.exception;

import java.sql.SQLException;

public class RegistrationException extends SQLException {
    public RegistrationException(String message, Throwable cause) {
        super(message,cause);
    }
}
