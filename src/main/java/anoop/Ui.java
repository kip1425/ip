package anoop;

import java.util.Scanner;

import anoop.task.Task;
import anoop.task.TaskList;



/**
 * Handles user interaction with the Anoop chatbot.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    /** Scanner to read user input from the console. */
    private final Scanner sc = new Scanner(System.in);

    /**
     * Reads one line of input from the user.
     *
     * @return a String representing the user's input.
     */
    public String readInput() {
        return sc.nextLine();
    }

    /**
     * Helper method to display a message to the user.
     *
     * @param input the message to display.
     */
    private String showMessage(String... input) {
        assert input != null : "input lines must not be null";
        String res = String.join(System.lineSeparator(), input) + System.lineSeparator();
        return res;
    }

    /**
     * Shows a message indicating a task has been added.
     *
     * @param t task that was added.
     * @param taskList task list containing the added task.
     */
    public String showTaskAdded(Task t, TaskList taskList) {
        assert t != null : "task must not be null";
        assert taskList != null : "taskList must not be null";
        return showMessage(LINE,
                "Got it. I've added this task:",
                t.toString(),
                "Now you have %d task(s) in the list.".formatted(taskList.getCurrentSize()),
                LINE);
    }

    /**
     * Shows the greeting message.
     */
    public String showGreeting() {
        return showMessage(LINE,
                "Hello! I'm Anoop",
                "What can I do for you?",
                LINE);
    }

    /**
     * Shows a message indicating a task has been marked as done.
     *
     * @param t task that was marked.
     */
    public String showMarked(Task t) {
        assert t != null : "task must not be null";
        return showMessage(LINE,
                "Nice! I've marked this task as done:",
                t.toString(),
                LINE);
    }

    /**
     * Shows a message indicating a task has been unmarked.
     *
     * @param t task that was unmarked.
     */
    public String showUnmarked(Task t) {
        assert t != null : "task must not be null";
        return showMessage(LINE,
                "OK, I've marked this task as not done yet:",
                t.toString(),
                LINE);
    }

    /**
     * Shows the goodbye message.
     */
    public String showBye() {
        return showMessage(LINE,
                "Bye. Hope to see you again soon!",
                LINE);
    }

    /**
     * Shows the list of tasks.
     *
     * @param taskList task list to show.
     */
    public String showList(TaskList taskList) {
        assert taskList != null : "taskList must not be null";
        return showMessage(LINE,
                taskList.toString(),
                LINE);
    }

    /**
     * Shows a message indicating a task has been deleted.
     *
     * @param t task that was deleted.
     * @param taskList task list after deletion.
     */
    public String showDelete(Task t, TaskList taskList) {
        assert t != null : "task must not be null";
        assert taskList != null : "taskList must not be null";
        return showMessage(LINE,
                "Noted. I've removed this task:",
                t.toString(),
                "Now you have %d task(s) in the list.".formatted(taskList.getCurrentSize()),
                LINE);
    }

    /**
     * Shows a message displaying the tasks matching the keyword.
     *
     * @param taskList new task list obtained after finding using keyword.
     */
    public String showFind(TaskList taskList) {
        assert taskList != null : "taskList must not be null";
        if (taskList.getCurrentSize() <= 0) {
            return showMessage(LINE,
                    "There are no tasks which match the keyword.",
                    LINE);
        }
        return showMessage(LINE,
                "Here are the matching tasks in your list:",
                taskList.toString(),
                LINE);
    }

    /**
     * Displays an error message.
     */
    public String showError(String msg) {
        assert msg != null : "error message must not be null";
        return msg;
    }
}
