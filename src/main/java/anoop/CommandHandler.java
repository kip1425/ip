package anoop;

import anoop.exception.InvalidTaskIndexException;
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
     * @param input the raw input string of the user.
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
                case MARK: {
                    String[] splitInput = input.trim().split("\\s+", 2);
                    int storageIndex = Integer.parseInt(splitInput[1]);
                    TaskStorage.markTaskAsDone(storageIndex);

                    return """
                            ____________________________________________________________
                            Nice! I've marked this task as done:
                              %s
                            ____________________________________________________________
                            """.formatted(TaskStorage.getTask(storageIndex));
                }
                case UNMARK: {
                    String[] splitInput = input.trim().split("\\s+", 2);
                    int storageIndex = Integer.parseInt(splitInput[1]);
                    TaskStorage.markTaskAsNotDone(storageIndex);

                    return """
                            ____________________________________________________________
                            Nice! I've marked this task as done:
                              %s
                            ____________________________________________________________
                            """.formatted(TaskStorage.getTask(storageIndex));
                }
                case TASK:
                    Task t = new Task(input);
                    TaskStorage.store(t);
                    return "added: " + input;
                default:
                    return String.format("""
                            ____________________________________________________________
                            %s
                            ____________________________________________________________
                            """, input);
            }
        } catch (StorageFullException | InvalidTaskIndexException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "Task number must be an integer.";
        }
    }
}

