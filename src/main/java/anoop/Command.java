package anoop;

/**
 * Represents commands which the chatbot can execute.
 */

public enum Command {
    GREETING("""
    ____________________________________________________________
    Hello! I'm Anoop
    What can I do for you?
    ____________________________________________________________
    """),
    
    BYE("""
    Bye. Hope to see you again soon!
    ____________________________________________________________
    """);

    private final String message;

    Command(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
