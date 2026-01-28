package anoop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    /** Start date/time of event */
    private final LocalDateTime start;

    /** End date/time of event */
    private final LocalDateTime end;

    /** Output format of date/time when toString(). */
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * Constructs an {@code Event} {@link Task} with a description.
     * Contains the start and end of the event.
     * @param description a description of the task.
     * @param isDone completion status of the task.
     * @param start the date/time the event starts.
     * @param end the date/time the event ends.
     */
    public Event(String description, boolean isDone, LocalDateTime start, LocalDateTime end) {
        super(description, isDone);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a string representation of the task with its status and task type.
     * Contains the date/time of when the event starts and ends.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.start.format(OUTPUT_FORMAT)
                + " to: " + this.end.format(OUTPUT_FORMAT) + ")";
    }
}
