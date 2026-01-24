package anoop;

import anoop.exception.StorageFullException;
import anoop.exception.InvalidTaskIndexException;

import java.util.ArrayList;

/**
 * Represents a storage class to store the user's tasks.
 */

public class TaskStorage {
    // Prevents instantiation.
    private TaskStorage() {
    }

    /** ArrayList to store users' tasks. */
    private static final ArrayList<Task> tasks = new ArrayList<>(100);
    /** Maximum capacity of task storage. */
    private static final int MAX_SIZE = 100;

    /**
     * Adds the user input task into a task storage.
     * If the storage is already full, an exception is thrown.
     *
     * @param task {@link Task} input from the user.
     * @throws StorageFullException if storage already contains 100 strings.
     */
    public static void store(Task task) throws StorageFullException {
        if (isStorageFull()) {
            throw new StorageFullException();
        }
        tasks.add(task);
    }

    /**
     * Returns true if TaskStorage is full and false otherwise.
     */
    public static boolean isStorageFull() {
        return tasks.size() >= MAX_SIZE;
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index the 1-based index of the task to mark
     * @throws InvalidTaskIndexException if the index is invalid
     */
    public static void markTaskAsDone(int index) throws InvalidTaskIndexException {
        if (index < 1 || index > tasks.size()) {
            throw new InvalidTaskIndexException(index);
        }

        Task t = tasks.get(index - 1); // convert 1-based to 0-based
        t.markAsDone();
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index the 1-based index of the task to unmark.
     * @throws InvalidTaskIndexException if the index is invalid.
     */
    public static void markTaskAsNotDone(int index) throws InvalidTaskIndexException {
        if (index < 1 || index > MAX_SIZE) {
            throw new InvalidTaskIndexException(index);
        }

        Task t = tasks.get(index - 1); // convert 1-based to 0-based
        t.markAsNotDone();
    }

    /**
     * Returns the task at the 1-based index.
     *
     * @param index the 1-based index of the task to get.
     * @return the task at the 1-based index of the TaskStorage.
     * @throws InvalidTaskIndexException if the index is invalid.
     */
    public static Task getTask(int index) throws InvalidTaskIndexException {
        return tasks.get(index - 1); // convert 1-based to 0-based;
    }

    /**
     * Returns a string formatted as a list of the tasks stored.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("____________________________________________________________\n");

        for (int i = 0; i < tasks.size(); i++) {
            // convert 0-based to 1-based
            sb.append(i + 1).append(". ").append(tasks.get(i).toString()).append("\n");
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
