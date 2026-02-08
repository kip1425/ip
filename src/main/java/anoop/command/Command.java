package anoop.command;

import anoop.Storage;
import anoop.Ui;
import anoop.exception.AnoopException;
import anoop.task.TaskList;


/**
 * Represents commands which the chatbot can execute.
 */

public abstract class Command {
    public abstract void execute(Ui ui, Storage storage, TaskList taskList) throws AnoopException;

    /**
     * Returns false to indicate this command should not exit the application.
     *
     * @return false
     */
    public boolean isExit() {
        return false;
    }
}

