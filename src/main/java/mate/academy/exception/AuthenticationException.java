package mate.academy.exception;

import java.sql.SQLException;

public class AuthenticationException extends SQLException {
    public AuthenticationException(String reason) {
        super(reason);
    }
}
