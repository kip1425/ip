package anoop.exception;

/**
 * Represents a base exception type for Anoop application exceptions.
 */
public class AnoopException extends Exception {
    /** Constructor for AnoopException, includes the error message. */
    public AnoopException(String s) {
        super(s);
    }
}
