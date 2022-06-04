package mate.academy.exception;

import java.io.IOException;

public class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
    }
}
