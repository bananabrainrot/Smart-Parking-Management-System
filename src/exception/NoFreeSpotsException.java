package exception;

public class NoFreeSpotsException extends RuntimeException {
    public NoFreeSpotsException(String message) {
        super(message);
    }
}
