package exception;

public class StatusException extends Exception {

    public StatusException() {
        super("Unknown watch status.");
    }

    public StatusException(String status) {
        super("Unknown watch status: " + status);
    }
}
