package anoop.command;

import anoop.Storage;
import anoop.Ui;
import anoop.exception.AnoopException;
import anoop.task.TaskList;

/**
 * Represents a command that undoes the most recent undoable command.
 */
public class UndoCommand extends Command {

    private final CommandHistory history;

    /**
     * Creates an undo command backed by the provided command history.
     *
     * @param history command history to read from.
     */
    public UndoCommand(CommandHistory history) {
        this.history = history;
    }

    /**
     * Undoes the latest command in history and persists the updated task list.
     *
     * @param ui user interface to generate responses.
     * @param storage storage to save the updated task list.
     * @param taskList task list to operate on.
     * @return response indicating whether undo was performed.
     * @throws AnoopException if undo fails or saving fails.
     */
    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList)
            throws AnoopException {

        if (history.isEmpty()) {
            return "Nothing to undo!";
        }

        Command last = history.pop();
        last.undo(taskList);

        storage.saveTasks(taskList.getListOfTasks());

        return "Undid the last command.";
    }
}
