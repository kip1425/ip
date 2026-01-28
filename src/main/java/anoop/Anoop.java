package anoop;

import anoop.exception.InvalidTaskFormatException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the Anoop chatbot.
 */

public class Anoop {
    /** Handles user commands. */
    private CommandHandler ch;

    /** Handles input and output with the user. */
    private Ui ui;

    /**
     * Instantiates an Anoop chatbot instance.
     * Initialises the UI and attempts to load tasks from storage.
     * If loading fails due to an IOException, an empty task list is created.
     */
    public Anoop() {
        ui = new Ui();
        Storage storage = null;
        TaskList taskList = null;

        try {
            storage = new Storage();
            List<Task> tasks = storage.loadTasks();
            taskList = new TaskList(tasks);
            this.ch = new CommandHandler(taskList, storage);
        } catch (IOException e) {
            this.ui.showLoadingError();
            taskList = new TaskList();
            this.ch = new CommandHandler(taskList, null);
        }
    }

    /**
     * Begins the chatbot interaction loop.
     * Continuously reads the user input through the UI and parses it into commands.
     * Loop ends when the command "bye" is received.
     */
    public void run() {
        this.ui.showMessage(this.ch.handle(Command.GREETING, ""));

        while (true) {
            String input = this.ui.readInput();
            Command cmd = Parser.parse(input);

            ui.showMessage(this.ch.handle(cmd, input));

            if (cmd == Command.BYE) {
                break;
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
