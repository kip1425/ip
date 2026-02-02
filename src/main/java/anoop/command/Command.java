package anoop.command;

import anoop.Storage;
import anoop.Ui;
import anoop.task.TaskList;

import anoop.exception.AnoopException;

/**
 * Represents commands which the chatbot can execute.
 */

public abstract class Command {
    public abstract void execute(Ui ui, Storage storage, TaskList taskList) throws AnoopException;

    public boolean isExit() {
        return false;
    }
}

