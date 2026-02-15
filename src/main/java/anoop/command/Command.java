package anoop.command;

import anoop.Storage;
import anoop.Ui;
import anoop.exception.AnoopException;
import anoop.task.TaskList;

/**
 * Represents commands which the chatbot can execute.
 */
public abstract class Command {
    /**
     * Executes the command and returns the UI response string.
     *
     * @param ui user interface to generate responses.
     * @param storage storage used by the application.
     * @param taskList task list to operate on.
     * @return response string for the UI to display.
     * @throws AnoopException if command execution fails.
     */
    public abstract String execute(Ui ui, Storage storage, TaskList taskList) throws AnoopException;

    /**
     * Returns false to indicate this command should not exit the application.
     *
     * @return {@code false} by default.
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Returns false to indicate this command cannot be undone.
     *
     * @return {@code false} by default.
     */
    public boolean isUndoable() {
        return false;
    }

    /**
     * Undoes the most recent undoable command.
     *
     * @param taskList task list to operate on.
     * @throws AnoopException if command cannot be undone.
     */
    public void undo(TaskList taskList) throws AnoopException {
        throw new AnoopException("This command cannot be undone.");
    }
}

