package anoop;

import anoop.command.*;
import anoop.exception.AnoopException;
import anoop.exception.InvalidTaskFormatException;
import anoop.task.Deadline;
import anoop.task.Event;
import anoop.task.Task;
import anoop.task.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a parser to parse the user's input string into a Command object.
 */
public class Parser {
    /** Input format of date/time. */
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    // Prevents instantiation.
    private Parser() {
    }

    /**
     * Returns a {@link Command} corresponding to user input string.
     *
     * @param input the raw input string of the user.
     * @return a {@link Command} corresponding to user input string.
     * @throws AnoopException
     */
    public static Command parse(String input) throws AnoopException {
        String[] split = input.trim().split("\\s+", 2);
        String cmdString = split[0].toLowerCase();
        String args = (split.length == 2) ? split[1].trim() : "";

        Command cmd = null;

        return switch (cmdString) {
            case "bye" -> new ByeCommand();
            case "list" -> new ListCommand();
            case "mark" -> new MarkCommand(parseIndex(args, cmdString));
            case "unmark" -> new UnmarkCommand(parseIndex(args, cmdString));
            case "todo" -> new AddCommand(parseTodo(args));
            case "deadline" -> new AddCommand(parseDeadline(args));
            case "event" -> new AddCommand(parseEvent(args));
            case "delete" -> new DeleteCommand(parseIndex(args, cmdString));
            case "find" -> new FindCommand(args);
            default -> throw new AnoopException("Unknown command entered.");
        };
    }

    /**
     * Parses the task index from the argument string.
     *
     * @param args argument string for the command.
     * @param cmd command keyword for error messages.
     * @return the task index.
     * @throws AnoopException
     */
    private static int parseIndex(String args, String cmd) throws AnoopException {
        if (args.isEmpty()) {
            throw new AnoopException("Missing an integer argument to the" + cmd + "command.");
        }
        try {
            return Integer.parseInt(args.trim());
        } catch (NumberFormatException e) {
            throw new AnoopException("Task number must be an integer.");
        }
    }

    /**
     * Parses a todo task from the argument string.
     *
     * @param args argument string for the todo description.
     * @return a {@link Todo} task.
     * @throws InvalidTaskFormatException
     */
    private static Task parseTodo(String args) throws InvalidTaskFormatException {
        if (args.isEmpty()) {
            throw new InvalidTaskFormatException("Todo description cannot be empty.");
        }
        return new Todo(args, false);
    }

    /**
     * Parses a deadline task from the argument string.
     *
     * @param args argument string containing description and deadline.
     * @return a {@link Deadline} task.
     * @throws InvalidTaskFormatException
     */
    private static Task parseDeadline(String args) throws InvalidTaskFormatException {
        try {
            String[] split = args.split("/by", 2);
            if (split.length != 2) {
                throw new InvalidTaskFormatException("The command format is \"(description) /by (date)\"");
            }

            String description = split[0].trim();
            String by = split[1].trim();

            if (description.isEmpty() || by.isEmpty()) {
                throw new InvalidTaskFormatException("Deadline description or date/time cannot be empty.");
            }

            LocalDateTime byDateTime = LocalDateTime.parse(by, INPUT_FORMAT);
            return new Deadline(description, false, byDateTime);

        } catch (DateTimeParseException e) {
            throw new InvalidTaskFormatException("Invalid date/time format. " +
                    "Use yyyy-MM-dd HHmm (e.g. 2026-01-29 2000).");
        }
    }

    /**
     * Parses an event task from the argument string.
     *
     * @param args argument string containing description, start, and end.
     * @return an {@link Event} task.
     * @throws InvalidTaskFormatException
     */
    private static Task parseEvent(String args) throws InvalidTaskFormatException {
        try {
            String[] split = args.split("/from|/to", 3);
            if (split.length != 3) {
                throw new InvalidTaskFormatException("The command format is \"(description) "
                        + "/from (date)"
                        + "/to (date)\"");
            }

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

        } catch (DateTimeParseException e) {
            throw new InvalidTaskFormatException("Invalid date/time format. " +
                    "Use yyyy-MM-dd HHmm (e.g. 2026-01-29 2000).");
        }
    }
}
