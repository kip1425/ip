package anoop;

import anoop.exception.InvalidTaskIndexException;
import anoop.exception.StorageFullException;
import anoop.exception.InvalidTaskFormatException;

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
                            OK, I've marked this task as not done yet:
                              %s
                            ____________________________________________________________
                            """.formatted(TaskStorage.getTask(storageIndex));
                }
                case TASK:
                    Task t = TaskFactory.createTask(input);
                    TaskStorage.store(t);
                    return """
                            ____________________________________________________________
                            Got it. I've added this task:
                              %s
                            Now you have %d task(s) in the list.
                            ____________________________________________________________
                            """.formatted(t.toString(), TaskStorage.getCurrentSize());
                default:
                    return """
                            ____________________________________________________________
                              %s
                            ____________________________________________________________
                            """.formatted(input);
            }
        } catch (StorageFullException | InvalidTaskIndexException | InvalidTaskFormatException e) {
            return e.getMessage();
        } catch (NumberFormatException e) { // For instances like "mark two" where user did not input an int
            return "Task number must be an integer.";
        }
    }
}

