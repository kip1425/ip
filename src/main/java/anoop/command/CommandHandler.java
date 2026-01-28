package anoop.command;

import anoop.Storage;
import anoop.exception.InvalidTaskIndexException;
import anoop.exception.ListFullException;
import anoop.exception.InvalidTaskFormatException;

import anoop.task.Task;
import anoop.task.TaskFactory;
import anoop.task.TaskList;

import java.io.IOException;

/**
 * Represents handler for chatbot commands.
 */

public class CommandHandler {
    private final TaskList taskList;
    private final Storage storage;

    public CommandHandler(TaskList taskList, Storage storage) {
        this.taskList = taskList;
        this.storage = storage;
    }

    /**
     * Returns a string for the chatbot to print depending on the {@link Command} received.
     *
     * @param cmd the {@link Command} type representing the user's command.
     * @param input the raw input string of the user.
     * @return a string representing the chatbot's response.
     */
    public String handle(Command cmd, String input) {
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
                    return this.taskList.toString();
                case MARK: {
                    String[] splitInput = input.trim().split("\\s+", 2);
                    int listIndex = Integer.parseInt(splitInput[1]);
                    this.taskList.markTaskAsDone(listIndex);
                    storage.saveTasks(this.taskList.getListOfTasks());

                    return """
                            ____________________________________________________________
                            Nice! I've marked this task as done:
                              %s
                            ____________________________________________________________
                            """.formatted(this.taskList.getTask(listIndex));
                }
                case UNMARK: {
                    String[] splitInput = input.trim().split("\\s+", 2);
                    int listIndex = Integer.parseInt(splitInput[1]);
                    this.taskList.markTaskAsNotDone(listIndex);
                    storage.saveTasks(this.taskList.getListOfTasks());

                    return """
                            ____________________________________________________________
                            OK, I've marked this task as not done yet:
                              %s
                            ____________________________________________________________
                            """.formatted(this.taskList.getTask(listIndex));
                }
                case TASK:
                    Task t = TaskFactory.createTask(input);
                    this.taskList.store(t);
                    storage.saveTasks(this.taskList.getListOfTasks());
                    return """
                            ____________________________________________________________
                            Got it. I've added this task:
                              %s
                            Now you have %d task(s) in the list.
                            ____________________________________________________________
                            """.formatted(t.toString(), this.taskList.getCurrentSize());
                case DELETE:
                    String[] splitInput = input.trim().split("\\s+", 2);
                    int listIndex = Integer.parseInt(splitInput[1]);
                    Task taskToDelete = this.taskList.getTask(listIndex);
                    this.taskList.deleteTask(listIndex);
                    storage.saveTasks(this.taskList.getListOfTasks());
                    return """
                            ____________________________________________________________
                            Noted. I've removed this task:
                              %s
                            Now you have %d task(s) in the list.
                            ____________________________________________________________
                            """.formatted(taskToDelete, this.taskList.getCurrentSize());
                case UNKNOWN:
                    return "Unknown command."; // Message when user enters unknown command.
                default:
                    return """
                            ____________________________________________________________
                              %s
                            ____________________________________________________________
                            """.formatted(input);
            }
        } catch (ListFullException | InvalidTaskIndexException | InvalidTaskFormatException e) {
            return e.getMessage();
        } catch (NumberFormatException e) { // For instances like "mark two" where user did not input an int
            return "Task number must be an integer.";
        } catch (IOException e) {
            return "Save/Load has failed.";
        }
    }
}

