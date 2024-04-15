package mate.academy.exception;

import java.util.NoSuchElementException;

public class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
    }
}
