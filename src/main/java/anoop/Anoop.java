package anoop;

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
        Scanner sc = new Scanner(System.in);

        System.out.println(CommandHandler.handle(Command.GREETING, ""));

        while (sc.hasNext()) {
            String input = sc.nextLine();
            Command cmd = Parser.parse(input);

            System.out.println(CommandHandler.handle(cmd, input));

            if (cmd == Command.BYE) {
                break;
            }
        }

        sc.close();
    }
}
