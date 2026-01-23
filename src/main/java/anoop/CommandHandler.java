package anoop;

import anoop.exception.StorageFullException;

/**
 * Represents handler for chatbot commands.
 */

public class CommandHandler {
    // Prevents instantiation.
    private CommandHandler() {
    }

    /**
     * Returns a string for the chatbot to print depending on the {@link Command} received.
     *
     * @param cmd the {@link Command} type representing the user's command.
     * @param input the raw input string of the user, used when cmd is TASK.
     * @return a string representing the chatbot's response.
     */
    public static String handle(Command cmd, String input) {
        try {
            switch (cmd) {
                case GREETING:
                    return """
                            ____________________________________________________________
                            Hello! I'm Anoop
                            What can I do for you?
                            ____________________________________________________________
                            """;
                case BYE:
                    return """
                            ____________________________________________________________
                            Bye. Hope to see you again soon!
                            ____________________________________________________________
                            """;
                case LIST:
                    return TaskStorage.taskstoString();
                case TASK:
                    TaskStorage.store(input);
                    return "added: " + input;
                default:
                    return String.format("""
                            ____________________________________________________________
                            %s
                            ____________________________________________________________
                            """, input);
            }
        } catch (StorageFullException e) {
            return e.getMessage();
        }
    }
}

