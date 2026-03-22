package anoop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import anoop.command.AddCommand;
import anoop.command.ByeCommand;
import anoop.command.Command;
import anoop.command.CommandHistory;
import anoop.command.DeleteCommand;
import anoop.command.FindCommand;
import anoop.command.ListCommand;
import anoop.command.MarkCommand;
import anoop.command.UndoCommand;
import anoop.command.UnmarkCommand;
import anoop.exception.AnoopException;
import anoop.exception.InvalidTaskFormatException;
import anoop.task.Deadline;
import anoop.task.Event;
import anoop.task.Task;
import anoop.task.Todo;

/**
 * Represents a parser to parse the user's input string into a Command object.
 */
public class Parser {
    /** Input format of date/time. */
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final String DATE_TIME_HINT = "Use yyyy-MM-dd HHmm (e.g. 2026-01-29 2000).";
    private static final CommandHistory history = new CommandHistory();

    // Prevents instantiation.
    private Parser() {
    }

    /**
     * Adds command to command history if command is undoable.
     * @param cmd command to be added to history.
     */
    public static void addToHistory(Command cmd) {
        if (cmd.isUndoable()) {
            history.push(cmd);
        }
    }

    /**
     * Returns a {@link Command} corresponding to user input string.
     *
     * @param input the raw input string of the user.
     * @return a {@link Command} corresponding to user input string.
     * @throws AnoopException
     */
    public static Command parse(String input) throws AnoopException {
        String trimmedInput = input == null ? "" : input.trim();
        if (trimmedInput.isEmpty()) {
            throw new AnoopException("Command cannot be empty.");
        }

        assert input != null : "input must not be null";

        String[] split = input.trim().split("\\s+", 2);
        assert split.length > 2 : "split has failed";

        String cmdString = split[0].toLowerCase();
        String args = (split.length == 2) ? split[1].trim() : "";

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
        case "undo" -> new UndoCommand(history);
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
        assert args != null : "args must not be null";
        assert cmd != null : "cmd must not be null";

        if (args.isEmpty()) {
            throw new AnoopException("Missing an integer argument to the " + cmd + " command.");
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
        assert args != null : "args must not be null";

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
        assert args != null : "args must not be null";

        String[] split = args.split("/by", 2);
        if (split.length != 2) {
            throw new InvalidTaskFormatException("The command format is \"(description) /by (date)\"");
        }

        String description = split[0].trim();
        String by = split[1].trim();

        if (description.isEmpty() || by.isEmpty()) {
            throw new InvalidTaskFormatException("Deadline description or date/time cannot be empty.");
        }

        LocalDateTime byDateTime = parseDateTime(by);

        return new Deadline(description, false, byDateTime);
    }

    /**
     * Parses an event task from the argument string.
     *
     * @param args argument string containing description, start, and end.
     * @return an {@link Event} task.
     * @throws InvalidTaskFormatException
     */
    private static Task parseEvent(String args) throws InvalidTaskFormatException {
        assert args != null : "args must not be null";

        int fromIndex = args.indexOf("/from");
        int toIndex = args.indexOf("/to");
        if (fromIndex < 0 || toIndex < 0 || toIndex <= fromIndex) {
            throw new InvalidTaskFormatException("The command format is \"(description) /from (date) /to (date)\"");
        }

        String description = args.substring(0, fromIndex).trim();
        String start = args.substring(fromIndex + "/from".length(), toIndex).trim();
        String end = args.substring(toIndex + "/to".length()).trim();

        if (description.isEmpty() || start.isEmpty() || end.isEmpty()) {
            throw new InvalidTaskFormatException("Event description or start and end date/time cannot be empty.");
        }

        LocalDateTime startDateTime = parseDateTime(start);
        LocalDateTime endDateTime = parseDateTime(end);

        if (endDateTime.isBefore(startDateTime)) {
            throw new InvalidTaskFormatException("Event end date/time cannot be before start date/time.");
        }

        return new Event(description, false, startDateTime, endDateTime);
    }

    private static LocalDateTime parseDateTime(String rawDateTime) throws InvalidTaskFormatException {
        try {
            return LocalDateTime.parse(rawDateTime, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new InvalidTaskFormatException("Invalid date/time format. " + DATE_TIME_HINT);
        }
    }
}
