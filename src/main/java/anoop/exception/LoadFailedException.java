package anoop.exception;

/**
 * Represents an exception when the task list fails to load from storage.
 */
public class LoadFailedException extends AnoopException {
    /** Constructor for LoadFailedException, includes default error message. */
    public LoadFailedException() {
        super("Failed to load tasks.");
    }
}
