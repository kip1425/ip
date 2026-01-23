package anoop.exception;

/**
 * Represents an exception when user attempts to add task after task storage is already full.
 */

public class StorageFullException extends Exception {
    public StorageFullException() {
        super("Storage is full and cannot accept more tasks!");
    }
}
