package anoop.exception;

/**
 * Represents an exception when user attempts to mark task which is out of storage range.
 */
public class InvalidTaskIndexException extends AnoopException {
    /** Constructor for InvalidTaskIndexException, includes which index is invalid. */
    public InvalidTaskIndexException(int index) {
        super("This task doesn't exist: " + index);
    }
}
