package exception;

public class InvalidVehiclePlateException extends RuntimeException {
    public InvalidVehiclePlateException(String message) {
        super(message);
    }
}
