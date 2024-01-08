package mate.academy.exception;

public class MovieSessionException extends Exception {
    public MovieSessionException(String message) {
        super(message);
    }

    public MovieSessionException(String message, Throwable e) {
        super(message, e);
    }
}
