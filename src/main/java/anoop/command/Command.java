package anoop.command;

/**
 * Represents commands which the chatbot can execute.
 */

public enum Command {
    GREETING, // Greet the user
    BYE,      // Exit the chatbot
    TASK,     // Add a task
    LIST,     // List all tasks
    MARK,     // Marks task as done
    UNMARK,   // Marks task as not done
    DELETE,   // Delete a task
    UNKNOWN;  // Unknown command
}

