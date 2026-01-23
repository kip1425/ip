package anoop;

/**
 * Represents the Anoop chatbot.
 */

public class Anoop {
    public static void main(String[] args) {
        String greetingMessage = Command.GREETING.getMessage();
        String exitMessage = Command.BYE.getMessage();

        System.out.println(greetingMessage + "\n" + exitMessage);
    }
}
