package anoop.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Stores recently executed undoable commands for use by {@link UndoCommand}.
 */
public class CommandHistory {
    private final Deque<Command> history;
    private final int capacity = 20;

    /**
     * Creates an empty command history with a fixed capacity.
     */
    public CommandHistory() {
        this.history = new ArrayDeque<>();
    }

    /**
     * Adds a command to history, evicting the oldest command when full.
     *
     * @param command command to store.
     */
    public void push(Command command) {
        if (history.size() == capacity) {
            history.removeFirst();
        }
        history.addLast(command);
    }

    /**
     * Returns whether there are no commands to undo.
     *
     * @return {@code true} if history has no commands.
     */
    public boolean isEmpty() {
        return history.isEmpty();
    }

    /**
     * Removes and returns the most recently stored command.
     *
     * @return last command in history.
     */
    public Command pop() {
        return history.removeLast();
    }
}
