package mate.academy.exception;

public class RegistrationException extends Exception {
    public RegistrationException() {
        super("Ошибка регистрации");
    }

    // Конструктор с пользовательским сообщением
    public RegistrationException(String message) {
        super(message);
    }

    // Конструктор с сообщением и причиной исключения
    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
