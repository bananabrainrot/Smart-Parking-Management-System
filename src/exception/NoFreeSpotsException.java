package exception;

public class NoFreeSpotsException extends RuntimeException {
    public NoFreeSpotsException() {
        super("There is no available spots");
    }
}
