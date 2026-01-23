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
        if (input.trim().equals("bye")) {
            return Command.BYE;
        } else if (input.trim().equals("list")) {
            return Command.LIST;
        }
        return Command.TASK;
    }
}
