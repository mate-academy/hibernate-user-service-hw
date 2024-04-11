package mate.academy.exception;

import java.util.NoSuchElementException;

public class AuthenticationException extends NoSuchElementException {
    public AuthenticationException(String s) {
        super(s);
    }
}
