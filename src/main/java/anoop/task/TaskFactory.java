package anoop.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import anoop.exception.InvalidTaskFormatException;

/**
 * A class that contains a factory method to instantiate different types of {@link Task}.
 */
public class TaskFactory {
    /** Output format of date/time. */
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    // Prevents instantiation.
    private TaskFactory() {
    }

    /**
     * Factory method to instantiate a {@link Task} depending on the task's string representation.
     *
     * @param type the task type identifier.
     * @param isDone task completion status.
     * @param args variable arguments containing task-specific fields.
     * @return a {@link Task} object depending on the data input.
     * @throws InvalidTaskFormatException if task type is not valid.
     */
    public static Task createTaskFromData(char type, boolean isDone, String... args) throws InvalidTaskFormatException {
        try {
            return switch (type) {
                case 'T' -> new Todo(args[0], isDone);
                case 'D' -> {
                    LocalDateTime by = LocalDateTime.parse(args[1], OUTPUT_FORMAT);
                    yield new Deadline(args[0], isDone, by);
                }
                case 'E' -> {
                    LocalDateTime from = LocalDateTime.parse(args[1], OUTPUT_FORMAT);
                    LocalDateTime to = LocalDateTime.parse(args[2], OUTPUT_FORMAT);
                    yield new Event(args[0], isDone, from, to);
                }
                default -> throw new InvalidTaskFormatException("Invalid task format.");
            };
        } catch (DateTimeParseException e) {
            throw new InvalidTaskFormatException("Invalid task format.");
        }
    }

}
