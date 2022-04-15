package mate.academy.exception;

public class AlreadyExistingEmailException extends Exception {
    public AlreadyExistingEmailException(String message) {
        super(message);
    }
}
