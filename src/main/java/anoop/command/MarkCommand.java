package anoop.command;

import anoop.Storage;
import anoop.Ui;
import anoop.exception.AnoopException;
import anoop.task.Task;
import anoop.task.TaskList;

/**
 * Represents a command to mark a task as done in the task list.
 */
public class MarkCommand extends Command {
    private final int index;

    /**
     * Creates a mark command for the task at the given index.
     *
     * @param index index of task to mark as done.
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Marks the task as done and saves the updated list to the storage.
     *
     * @param ui user interface to show task marked message.
     * @param storage storage to save the updated task list.
     * @param taskList task list containing the task to mark.
     * @return response string for the UI to display.
     * @throws AnoopException when index is not valid or saving fails.
     */
    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) throws AnoopException {
        assert ui != null : "ui must not be null";
        assert storage != null : "storage must not be null";
        assert taskList != null : "taskList must not be null";
        Task t = taskList.markTaskAsDone(index);
        assert t != null : "task should exist for a valid index";
        storage.saveTasks(taskList.getListOfTasks());
        return ui.showMarked(t);
    }

    /**
     * Reverts this command by marking the task as not done.
     *
     * @param taskList task list to operate on.
     * @throws AnoopException if the task cannot be unmarked.
     */
    @Override
    public void undo(TaskList taskList) throws AnoopException {
        taskList.markTaskAsNotDone(this.index);
    }

    /**
     * Returns {@code true} because mark operations can be undone.
     *
     * @return {@code true}.
     */
    @Override
    public boolean isUndoable() {
        return true;
    }
}
