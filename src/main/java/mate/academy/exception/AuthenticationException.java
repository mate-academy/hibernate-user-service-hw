package mate.academy.exception;

public class AuthenticationException extends Exception {
    // Конструктор по умолчанию
    public AuthenticationException() {
        super("Ошибка аутентификации");
    }

    // Конструктор с пользовательским сообщением
    public AuthenticationException(String message) {
        super(message);
    }

    // Конструктор с причиной исключения
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
