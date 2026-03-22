package anoop.task;

import java.util.ArrayList;
import java.util.List;

import anoop.exception.InvalidTaskIndexException;
import anoop.exception.ListFullException;

/**
 * Represents a list class to store the user's tasks.
 */
public class TaskList {
    /** Maximum capacity of task list. */
    private static final int MAX_SIZE = 100;
    /** ArrayList to store users' tasks. */
    private final List<Task> tasks;

    /**
     * Constructor for when loading tasks from disk is successful.
     */
    public TaskList(List<Task> tasks) {
        assert tasks != null : "tasks must not be null";

        this.tasks = tasks;
    }

    /**
     * Constructor when loading from disk fails.
     */
    public TaskList() {
        this(new ArrayList<>(MAX_SIZE));
    }

    /**
     * Searches for tasks in the task list which contain the keyword.
     *
     * @param keyword keyword to be used for searching.
     * @return a new TaskList containing the matching tasks.
     */
    public TaskList find(String keyword) {
        assert keyword != null : "keyword must not be null";
        List<Task> list = new ArrayList<>();

        for (Task t : tasks) {
            assert t != null : "stored tasks must not be null";
            if (t.getDescription().contains(keyword)) {
                list.add(t.clone());
            }
        }

        return new TaskList(list);
    }

    /**
     * Adds the user input task into a task list.
     * If the list is already full, an exception is thrown.
     *
     * @param task {@link Task} input from the user.
     * @throws ListFullException if list already contains 100 strings.
     */
    public void store(Task task) throws ListFullException {
        assert task != null : "task must not be null";

        if (isListFull()) {
            throw new ListFullException();
        }

        this.tasks.add(task);
    }

    /**
     * Returns true if TaskList is full and false otherwise.
     */
    public boolean isListFull() {
        assert this.tasks != null : "tasks list must be initialized";

        return this.tasks.size() >= MAX_SIZE;
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index the 1-based index of the task to mark.
     * @return the marked task.
     * @throws InvalidTaskIndexException if the index is invalid.
     */
    public Task markTaskAsDone(int index) throws InvalidTaskIndexException {
        assert this.tasks != null : "tasks list must be initialized";
        Task task = getTask(index);
        task.markAsDone();

        return task;
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index the 1-based index of the task to unmark.
     * @return the unmarked task.
     * @throws InvalidTaskIndexException if the index is invalid.
     */
    public Task markTaskAsNotDone(int index) throws InvalidTaskIndexException {
        assert this.tasks != null : "tasks list must be initialized";
        Task task = getTask(index);
        task.markAsNotDone();

        return task;
    }

    /**
     * Returns the task at the 1-based index.
     *
     * @param index the 0-based index of the task to get.
     * @return the task from the TaskList.
     * @throws InvalidTaskIndexException if the index is invalid.
     */
    public Task getTask(int index) throws InvalidTaskIndexException {
        assert this.tasks != null : "tasks list must be initialized";

        return this.tasks.get(getZeroBasedIndex(index));
    }

    /**
     * Getter method to get the list of tasks.
     *
     * @return a List object containing the tasks.
     */
    public List<Task> getListOfTasks() {
        assert this.tasks != null : "tasks list must be initialized";

        return this.tasks;
    }

    /**
     * Getter method to get current number of tasks stored.
     *
     * @return current number of tasks stored.
     */
    public int getCurrentSize() {
        assert this.tasks != null : "tasks list must be initialized";

        return this.tasks.size();
    }

    /**
     * Deletes the task at the 1-based index
     *
     * @param index the 1-based index of the task to delete.
     * @return the deleted task.
     * @throws InvalidTaskIndexException if the index is invalid.
     */
    public Task deleteTask(int index) throws InvalidTaskIndexException {
        assert this.tasks != null : "tasks list must be initialized";

        return this.tasks.remove(getZeroBasedIndex(index));
    }

    /**
     * Converts a 1-based task index to a 0-based array index after validation.
     */
    private int getZeroBasedIndex(int index) throws InvalidTaskIndexException {
        if (index < 1 || index > this.tasks.size()) {
            throw new InvalidTaskIndexException(index);
        }

        return index - 1;
    }

    /**
     * Returns a string formatted as a list of the tasks stored.
     */
    @Override
    public String toString() {
        assert this.tasks != null : "tasks list must be initialized";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {
            // convert 0-based to 1-based
            sb.append(i + 1).append(". ").append(tasks.get(i).toString()).append("\n");
        }

        return sb.toString();
    }
}
