package anoop.command;

/**
 * Represents a parser to parse the user's input string into a Command object.
 */

public class Parser {
    // Prevents instantiation.
    private Parser() {
    }

    /**
     * Returns a {@link Command} corresponding to user input string.
     *
     * @param input the raw input string of the user.
     * @return a {@link Command} corresponding to user input string.
     */
    public static Command parse(String input) {
        String trimmed = input.trim();

        if (trimmed.isEmpty()) {
            return Command.UNKNOWN;
        } else if (trimmed.equals("bye")) {
            return Command.BYE;
        } else if (trimmed.equals("list")) {
            return Command.LIST;
        } else if (trimmed.startsWith("mark ")) {
            return Command.MARK;
        } else if (trimmed.startsWith(("unmark "))) {
            return Command.UNMARK;
        } else if (trimmed.startsWith("todo ") || trimmed.startsWith("deadline ") || trimmed.startsWith("event ")) {
            return Command.TASK;
        } else if (trimmed.startsWith("delete ")) {
            return Command.DELETE;
        } else {
            return Command.UNKNOWN; // anything else is UNKNOWN
        }
    }
}
