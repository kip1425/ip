package anoop;

import anoop.exception.InvalidTaskFormatException;

/**
 * A class that contains a factory method to instantiate different types of {@link Task}.
 */
public class TaskFactory {
    /** Offsets to get substring of user input string. */
    private static final int TODO_OFFSET = 5;
    private static final int DEADLINE_OFFSET = 9;
    private static final int EVENT_OFFSET = 6;

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
                return new Deadline(description, false, by);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new InvalidTaskFormatException("\"/by\" must be included before date/time.");
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
                return new Event(description, false, start, end);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new InvalidTaskFormatException("\"/from\" and \"/to\" must be included before date/time.");
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
     * @return a {@link Task} object depending on the input.
     * @throws InvalidTaskFormatException if task type is not valid.
     */
    public static Task createTaskFromData(char type, boolean isDone, String... args) throws InvalidTaskFormatException {
        return switch (type) {
            case 'T' -> new Todo(args[0], isDone);
            case 'D' -> new Deadline(args[0], isDone, args[1]);
            case 'E' -> new Event(args[0], isDone, args[1], args[2]);
            default -> throw new InvalidTaskFormatException("Invalid task format.");
        };
    }

}
