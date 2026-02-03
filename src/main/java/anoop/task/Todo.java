package anoop.task;

/**
 * Represents a Todo {@link Task}.
 */
public class Todo extends Task{
    /**
     * Constructs a {@code Todo} {@link Task} with a description.
     * @param description a description of the task.
     * @param isDone completion status of the task.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns a semantic copy of the {@link Todo}.
     */
    @Override
    public Todo clone() {
        return new Todo(this.description, this.isDone);
    }

    /**
     * Returns a string representation of the task with its status and task type.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
