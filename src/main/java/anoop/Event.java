package anoop;

public class Event extends Task {
    /** Start date/time of event */
    private final String start;
    /** End date/time of event */
    private final String end;

    /**
     * Constructs an {@code Event} {@link Task} with a description.
     * Contains the start and end of the event.
     * @param description a description of the task.
     * @param start the date/time the event starts.
     * @param end the date/time the event ends.
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a string representation of the task with its status and task type.
     * Contains the date/time of when the event starts and ends.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }
}
