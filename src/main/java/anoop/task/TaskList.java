package anoop.task;

import anoop.exception.ListFullException;
import anoop.exception.InvalidTaskIndexException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list class to store the user's tasks.
 */

public class TaskList {
    /** ArrayList to store users' tasks. */
    private List<Task> tasks = new ArrayList<>(100);
    /** Maximum capacity of task list. */
    private static final int MAX_SIZE = 100;

    /**
     * Constructor for when loading tasks from disk is successful.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructor when loading from disk fails.
     */
    public TaskList() {

    }

    /**
     * Adds the user input task into a task list.
     * If the list is already full, an exception is thrown.
     *
     * @param task {@link Task} input from the user.
     * @throws ListFullException if list already contains 100 strings.
     */
    public void store(Task task) throws ListFullException {
        if (isListFull()) {
            throw new ListFullException();
        }
        this.tasks.add(task);
    }

    /**
     * Returns true if TaskList is full and false otherwise.
     */
    public boolean isListFull() {
        return this.tasks.size() >= MAX_SIZE;
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index the 1-based index of the task to mark
     * @throws InvalidTaskIndexException if the index is invalid
     */
    public void markTaskAsDone(int index) throws InvalidTaskIndexException {
        if (index < 1 || index > this.tasks.size()) {
            throw new InvalidTaskIndexException(index);
        }

        Task t = this.tasks.get(index - 1); // convert 1-based to 0-based
        t.markAsDone();
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index the 1-based index of the task to unmark.
     * @throws InvalidTaskIndexException if the index is invalid.
     */
    public void markTaskAsNotDone(int index) throws InvalidTaskIndexException {
        if (index < 1 || index > tasks.size()) {
            throw new InvalidTaskIndexException(index);
        }

        Task t = this.tasks.get(index - 1); // convert 1-based to 0-based
        t.markAsNotDone();
    }

    /**
     * Returns the task at the 1-based index.
     *
     * @param index the 1-based index of the task to get.
     * @return the task at the 1-based index of the TaskList.
     * @throws InvalidTaskIndexException if the index is invalid.
     */
    public Task getTask(int index) throws InvalidTaskIndexException {
        if (index < 1 || index > tasks.size()) {
            throw new InvalidTaskIndexException(index);
        }

        return this.tasks.get(index - 1); // convert 1-based to 0-based;
    }

    /**
     * Getter method to get the list of tasks.
     *
     * @return a List object containing the tasks.
     */
    public List<Task> getListOfTasks() {
        return this.tasks;
    }

    /**
     * Getter method to get current number of tasks stored.
     *
     * @return current number of tasks stored.
     */
    public int getCurrentSize() {
        return this.tasks.size();
    }

    /**
     * Deletes the task at the 1-based index
     *
     * @param index the 1-based index of the task to delete.
     * @throws InvalidTaskIndexException if the index is invalid.
     */
    public void deleteTask(int index) throws InvalidTaskIndexException {
        if (index < 1 || index > this.tasks.size()) {
            throw new InvalidTaskIndexException(index);
        }

        this.tasks.remove(index - 1); // convert 1-based to 0-based;
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
}
