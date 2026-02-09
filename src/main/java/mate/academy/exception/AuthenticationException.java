package mate.academy.exception;

import java.util.function.Supplier;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static Supplier<AuthenticationException> findByIdSupplier(String email, Class<?> clazz) {
        return () -> new AuthenticationException(
                "Can't find an %s by email %s".formatted(clazz.getName().toLowerCase(), email)
        );
    }
}
