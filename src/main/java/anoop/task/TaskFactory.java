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
            case 'T' -> createTodo(isDone, args);
            case 'D' -> createDeadline(isDone, args);
            case 'E' -> createEvent(isDone, args);
            default -> throw new InvalidTaskFormatException("Invalid task format.");
            };
        } catch (DateTimeParseException | IndexOutOfBoundsException e) {
            throw new InvalidTaskFormatException("Invalid task format.");
        }
    }

    private static Todo createTodo(boolean isDone, String... args) throws InvalidTaskFormatException {
        validateArgCount(args, 1);

        return new Todo(args[0], isDone);
    }

    private static Deadline createDeadline(boolean isDone, String... args) throws InvalidTaskFormatException {
        validateArgCount(args, 2);
        LocalDateTime by = LocalDateTime.parse(args[1], OUTPUT_FORMAT);

        return new Deadline(args[0], isDone, by);
    }

    private static Event createEvent(boolean isDone, String... args) throws InvalidTaskFormatException {
        validateArgCount(args, 3);
        LocalDateTime from = LocalDateTime.parse(args[1], OUTPUT_FORMAT);
        LocalDateTime to = LocalDateTime.parse(args[2], OUTPUT_FORMAT);

        return new Event(args[0], isDone, from, to);
    }

    private static void validateArgCount(String[] args, int expected) throws InvalidTaskFormatException {
        if (args.length < expected) {
            throw new InvalidTaskFormatException("Invalid task format.");
        }
    }
}
