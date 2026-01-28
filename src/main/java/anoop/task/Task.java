package anoop.task;

/**
 * Represents a user's task that can be marked or unmarked as done.
 */

public abstract class Task {
    /** Represents the description of the task. */
    protected final String description;

    /** Represents the task's completion status */
    protected boolean isDone;

    /**
     * Constructs a Task object with a description.
     * @param description a description of the task.
     * @param isDone completion status of the task.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Marks the task completion status as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task completion status as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Helper method to get completion status icon of task.
     * @return a string "X" if done or " " if not done.
     */
    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns a string representation of the task with its status.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
