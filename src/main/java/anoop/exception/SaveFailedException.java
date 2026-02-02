package anoop.exception;

/**
 * Represents an exception when the task list fails to save to storage.
 */
public class SaveFailedException extends AnoopException {
    /** Constructor for SaveFailedException, includes default error message. */
    public SaveFailedException() {
        super("Failed to save tasks.");
    }
}
