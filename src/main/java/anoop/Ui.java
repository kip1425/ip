package anoop;

import java.util.Scanner;

/**
 * Handles user interaction with the Anoop chatbot.
 */

public class Ui {
    /** Scanner to read user input from the console. */
    private final Scanner sc = new Scanner(System.in);

    /**
     * Reads one line of input from the user.
     * @return a String representing the user's input.
     */
    public String readInput() {
        return sc.nextLine();
    }

    /**
     * Displays a message to the user.
     * @param input the message to display.
     */
    public void showMessage(String input) {
        System.out.println(input);
    }

    /**
     * Displays an error message indicating tasks have failed to load form the storage.
     */
    public void showLoadingError() {
        System.out.println("Error occurred while loading tasks.");
    }
}
