package mate.academy.exception;

public class TicketInitializationException extends Exception {
    public TicketInitializationException(String message) {
        super(message);
    }

    public TicketInitializationException(String message, Throwable e) {
        super(message, e);
    }
}
