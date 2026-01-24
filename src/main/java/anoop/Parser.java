package anoop;

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
        String trimmedInput = input.trim();
        if (trimmedInput.equals("bye")) {
            return Command.BYE;
        } else if (trimmedInput.equals("list")) {
            return Command.LIST;
        } else if (trimmedInput.startsWith("mark")) {
            return Command.MARK;
        } else if (trimmedInput.startsWith(("unmark"))) {
            return Command.UNMARK;
        }
        return Command.TASK;
    }
}
