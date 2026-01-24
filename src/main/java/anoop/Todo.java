package anoop;

/**
 * Represents a Todo {@link Task}.
 */

public class Todo extends Task{
    /**
     * Constructs a {@code Todo} {@link Task} with a description.
     * @param description a description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the task with its status and task type.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
