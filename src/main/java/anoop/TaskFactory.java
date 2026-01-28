package anoop;

import anoop.exception.InvalidTaskFormatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A class that contains a factory method to instantiate different types of {@link Task}.
 */
public class TaskFactory {
    /** Offsets to get substring of user input string. */
    private static final int TODO_OFFSET = 5;
    private static final int DEADLINE_OFFSET = 9;
    private static final int EVENT_OFFSET = 6;

    /** Input/output format of date/time. */
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    // Prevents instantiation.
    private TaskFactory() {
    }

    /**
     * Factory method to instantiate different {@link Task} objects depending on user input.
     *
     * @param input the user input string.
     * @return a {@link Task} object corresponding to the user input.
     * @throws InvalidTaskFormatException when user input is invalid.
     */
    public static Task createTask(String input) throws InvalidTaskFormatException {
        String trimmed = input.trim();

        if (trimmed.startsWith("todo ")) { // Creates Todo task
            String description = trimmed.substring(TODO_OFFSET).trim();

            if (description.isEmpty()) {
                throw new InvalidTaskFormatException("Todo description cannot be empty.");
            }
            return new Todo(description, false);

        } else if (trimmed.startsWith("deadline ")) { // Creates Deadline task
            try {
                String[] split = trimmed.substring(DEADLINE_OFFSET).trim().split("/by", 2);
                String description = split[0].trim();
                String by = split[1].trim();

                if (description.isEmpty() || by.isEmpty()) {
                    throw new InvalidTaskFormatException("Deadline description or date/time cannot be empty.");
                }

                LocalDateTime byDateTime = LocalDateTime.parse(by, INPUT_FORMAT);
                return new Deadline(description, false, byDateTime);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new InvalidTaskFormatException("\"/by\" must be included before date/time.");
            } catch (DateTimeParseException e) {
                throw new InvalidTaskFormatException("Invalid date/time format. " +
                        "Use yyyy-MM-dd HHmm (e.g. 2026-01-29 2000).");
            }

        } else if (trimmed.startsWith("event ")) { // Creates Event task
            try {
                String[] split = trimmed.substring(EVENT_OFFSET).trim().split("/from|/to", 3);
                String description = split[0].trim();
                String start = split[1].trim();
                String end = split[2].trim();

                if (description.isEmpty() || start.isEmpty() || end.isEmpty()) {
                    throw new InvalidTaskFormatException("Event description or start and end date/time "
                            + "cannot be empty.");
                }

                LocalDateTime startDateTime = LocalDateTime.parse(start, INPUT_FORMAT);
                LocalDateTime endDateTime = LocalDateTime.parse(end, INPUT_FORMAT);
                return new Event(description, false, startDateTime, endDateTime);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new InvalidTaskFormatException("\"/from\" and \"/to\" must be included before date/time.");
            } catch (DateTimeParseException e) {
                throw new InvalidTaskFormatException("Invalid date/time format. " +
                        "Use yyyy-MM-dd HHmm (e.g. 2026-01-29 2000).");
            }
        } else {
            throw new InvalidTaskFormatException("Unknown task type. Must start with todo, deadline, or event.");
        }
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
                    LocalDateTime to = LocalDateTime.parse(args[1], OUTPUT_FORMAT);
                    yield new Event(args[0], isDone, from, to);
                }
                default -> throw new InvalidTaskFormatException("Invalid task format.");
            };
        } catch (DateTimeParseException e) {
            throw new InvalidTaskFormatException("Invalid task format.");
        }
    }

}
