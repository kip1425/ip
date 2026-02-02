package anoop.exception;

/**
 * Represents an exception when user attempts to create task with invalid format.
 */
public class InvalidTaskFormatException extends AnoopException {
    /** Constructor for InvalidTaskIndexException. */
    public InvalidTaskFormatException(String s) {
        super(s);
    }
}
