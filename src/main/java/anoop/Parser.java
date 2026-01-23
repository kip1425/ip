package anoop;

/**
 * Represents a parser to parse the user's input string into a Command object.
 */

public class Parser {
    public Command parse(String input) {
        if (input.trim().equals("bye")) {
            return Command.BYE;
        }
        return Command.UNKNOWN;
    }
}
