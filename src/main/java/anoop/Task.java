package anoop;

/**
 * Represents a user's task that can be marked or unmarked as done.
 */

public class Task {
    /** Represents the description of the task. */
    protected String description;

    /** Represents the task's completion status */
    private Boolean isDone = false;

    public Task(String description) {
        this.description = description;
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
     * Returns a string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
