package android.exception;

public class OnMainThreadException extends RuntimeException {

    public OnMainThreadException() {
        super();
    }

    public OnMainThreadException(String message) {
        super(message);
    }
}
