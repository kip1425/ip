package anoop.exception;

/**
 * Represents an exception when user attempts to add task after task storage is already full.
 */

public class ListFullException extends Exception {
    public ListFullException() {
        super("List is full and cannot accept more tasks!");
    }
}
