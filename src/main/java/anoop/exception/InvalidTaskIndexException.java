package anoop.exception;

/**
 * Represents an exception when user attempts to mark task which is out of storage range.
 */

public class InvalidTaskIndexException extends Exception {
    public InvalidTaskIndexException(int index) {
        super("This task doesn't exist: " + index);
    }
}
