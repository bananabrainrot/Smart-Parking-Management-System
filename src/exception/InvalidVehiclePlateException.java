package exception;

public class InvalidVehiclePlateException extends RuntimeException {
    public InvalidVehiclePlateException(String plate) {

        super("car plate" + plate + "is invalid");
    }
}
