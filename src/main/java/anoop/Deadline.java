package anoop;

public class Deadline extends Task {
    /** Date/time to complete the task by. */
    private final String by;

    /**
     * Constructs a {@code Deadline} {@link Task} with a description.
     * Contains a deadline to finish the task by.
     *
     * @param description a description of the task.
     * @param isDone completion status of the task.
     * @param by the date/time to complete the task by.
     */
    public Deadline(String description, boolean isDone, String by) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Returns a string representation of the task with its status and task type.
     * Contains the date/time to complete the task by.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
