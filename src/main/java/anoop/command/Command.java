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
     * @return false
     */
    public boolean isExit() {
        return false;
    }
}

