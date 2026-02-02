package anoop;

import anoop.command.Command;
import anoop.exception.AnoopException;
import anoop.exception.LoadFailedException;
import anoop.task.Task;
import anoop.task.TaskList;

import java.io.IOException;
import java.util.List;

/**
 * Represents the Anoop chatbot.
 */
public class Anoop {
    /** Handles input and output with the user. */
    private final Ui ui;
    private final Storage storage;
    private TaskList taskList;

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
            this.ui.showLoadingError();
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
}
