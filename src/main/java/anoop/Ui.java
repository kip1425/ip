package anoop;

import anoop.task.Task;
import anoop.task.TaskList;

import java.util.Scanner;

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
    private void showMessage(String... input) {
        String res = String.join(System.lineSeparator(), input) + System.lineSeparator();
        System.out.println(res);
    }

    /**
     * Shows a message indicating a task has been added.
     *
     * @param t task that was added.
     * @param taskList task list containing the added task.
     */
    public void showTaskAdded(Task t, TaskList taskList) {
        showMessage(LINE,
                "Got it. I've added this task:",
                t.toString(),
                "Now you have %d task(s) in the list.".formatted(taskList.getCurrentSize()),
                LINE);
    }

    /**
     * Shows the greeting message.
     */
    public void showGreeting() {
        showMessage(LINE,
                "Hello! I'm Anoop",
                "What can I do for you?",
                LINE);
    }

    /**
     * Shows a message indicating a task has been marked as done.
     *
     * @param t task that was marked.
     */
    public void showMarked(Task t) {
        showMessage(LINE,
                "Nice! I've marked this task as done:",
                t.toString(),
                LINE);
    }

    /**
     * Shows a message indicating a task has been unmarked.
     *
     * @param t task that was unmarked.
     */
    public void showUnmarked(Task t) {
        showMessage(LINE,
                "OK, I've marked this task as not done yet:",
                t.toString(),
                LINE);
    }

    /**
     * Shows the goodbye message.
     */
    public void showBye() {
        showMessage(LINE,
                "Bye. Hope to see you again soon!",
                LINE);
    }

    /**
     * Shows the list of tasks.
     *
     * @param taskList task list to show.
     */
    public void showList(TaskList taskList) {
        showMessage(LINE,
                taskList.toString(),
                LINE);
    }

    /**
     * Shows a message indicating a task has been deleted.
     *
     * @param t task that was deleted.
     * @param taskList task list after deletion.
     */
    public void showDelete(Task t, TaskList taskList) {
        showMessage(LINE,
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
    public void showFind(TaskList taskList) {
        if (taskList.getCurrentSize() <= 0) {
            showMessage(LINE,
                    "There are no tasks which match the keyword.",
                    LINE);
            return;
        }
        showMessage(LINE,
                "Here are the matching tasks in your list:",
                taskList.toString(),
                LINE);
    }

    /**
     * Displays an error message indicating tasks have failed to load from the storage.
     */
    public void showLoadingError() {
        System.out.println("Error occurred while loading tasks.");
    }
}
