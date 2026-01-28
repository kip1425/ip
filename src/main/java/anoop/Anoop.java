package anoop;

import anoop.exception.InvalidTaskFormatException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the Anoop chatbot.
 */

public class Anoop {
    /**
     * The main method that runs the Anoop chatbot.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Storage storage = null;
        TaskList taskList = null;
        CommandHandler ch = null;

        try {
            storage = new Storage();
            List<Task> tasks = storage.loadTasks();
            taskList = new TaskList(tasks);
            ch = new CommandHandler(taskList, storage);
        } catch (IOException e) {
            taskList = new TaskList();
            ch = new CommandHandler(taskList, null);
        }

        Scanner sc = new Scanner(System.in);

        System.out.println(ch.handle(Command.GREETING, ""));

        while (sc.hasNext()) {
            String input = sc.nextLine();
            Command cmd = Parser.parse(input);

            System.out.println(ch.handle(cmd, input));

            if (cmd == Command.BYE) {
                break;
            }
        }

        sc.close();
    }
}
