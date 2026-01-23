package anoop;

import java.util.Scanner;

/**
 * Represents the Anoop chatbot.
 */

public class Anoop {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CommandHandler handler = new CommandHandler();
        Parser parser = new Parser();

        System.out.println(handler.handle(Command.GREETING, ""));

        while (true) {
            String input = sc.nextLine();
            Command cmd = parser.parse(input);

            System.out.println(handler.handle(cmd, input));

            if (cmd == Command.BYE) {
                break;
            }
        }

        sc.close();
    }
}
