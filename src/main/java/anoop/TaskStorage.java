package anoop;

import anoop.exception.StorageFullException;

/**
 * Represents a storage class to store the user's tasks.
 */

public class TaskStorage {
    // Prevents instantiation.
    private TaskStorage() {
    }

    /** Array to store users' tasks. */
    private static final String[] tasks = new String[100];
    /** Index to track where to store next task in the array */
    private static int storageIndex = 0;

    /**
     * Adds the user input task into a task storage.
     * If the storage is already full, an exception is thrown.
     *
     * @param input string input from the user.
     * @throws StorageFullException if storage already contains 100 strings.
     */
    public static void store(String input) throws StorageFullException {
        if (storageIndex >= tasks.length) {
            throw new StorageFullException();
        }
        tasks[storageIndex++] = input;
    }

    /**
     * Returns a string formatted as a list of the tasks stored.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("____________________________________________________________\n");

        for (int i = 0; i < storageIndex; i++) {
            sb.append(i + 1).append(". " + tasks[i] + "\n");
        }

        sb.append("____________________________________________________________");
        return sb.toString();
    }

    /**
     * Static helper method to return the list of tasks without instantiation.
     */
    public static String taskstoString() {
        return new TaskStorage().toString();
    }
}
