package anoop;

import java.util.List;

import anoop.command.ByeCommand;
import anoop.command.Command;
import anoop.exception.AnoopException;
import anoop.task.Task;
import anoop.task.TaskList;
import javafx.stage.WindowEvent;

/**
 * Represents the Anoop chatbot.
 */
public class Anoop {
    /** Handles input and output with the user. */
    private final Ui ui;
    private final Storage storage;
    private TaskList taskList;
    private boolean isExitRequested;

    /**
     * Instantiates an Anoop chatbot instance.
     * Initialises the UI and attempts to load tasks from storage.
     * If loading fails due to an IOException, an empty task list is created.
     */
    public Anoop() {
        ui = new Ui();
        storage = new Storage();

        try {
            List<Task> tasks = storage.loadTasks();
            taskList = new TaskList(tasks);
        } catch (AnoopException e) {
            this.ui.showError(e.getMessage());
            taskList = new TaskList();
        }
    }

    /**
     * Begins the chatbot interaction loop.
     * Continuously reads the user input through the UI and parses it into commands.
     * Loop ends when the command "bye" is received.
     */
    public void run() {
        this.ui.showGreeting();

        while (true) {
            try {
                String input = this.ui.readInput();
                Command cmd = Parser.parse(input);

                cmd.execute(ui, storage, taskList);

                if (cmd.isExit()) {
                    break;
                }
            } catch (AnoopException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * The entry point of the chatbot.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Anoop chatBot = new Anoop();
        chatBot.run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        assert input != null : "input must not be null";
        try {
            Command cmd = Parser.parse(input.trim());
            Parser.addToHistory(cmd);
            String response = cmd.execute(ui, storage, taskList);
            isExitRequested = cmd.isExit();
            return response;
        } catch (AnoopException e) {
            isExitRequested = false;
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Returns whether the last command requested an exit, and clears the flag.
     *
     * @return true if the app should exit.
     */
    public boolean consumeExitRequest() {
        boolean shouldExit = isExitRequested;
        isExitRequested = false;
        return shouldExit;
    }

    public String greet() {
        assert ui != null : "ui must not be null";
        return this.ui.showGreeting();
    }
}
