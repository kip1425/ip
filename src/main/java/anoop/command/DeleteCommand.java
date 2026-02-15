package anoop.command;

import anoop.Storage;
import anoop.Ui;
import anoop.exception.AnoopException;
import anoop.task.Task;
import anoop.task.TaskList;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private Task task;
    private final int index;

    /**
     * Creates a delete command for the task at the given index.
     *
     * @param index index of task to delete.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Deletes the task from the task list and saves the updated list to the storage.
     *
     * @param ui user interface to show task deleted message.
     * @param storage storage to save the updated task list.
     * @param taskList task list containing the task to delete.
     * @return response string for the UI to display.
     * @throws AnoopException when index is not valid or saving fails.
     */
    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) throws AnoopException {
        assert ui != null : "ui must not be null";
        assert storage != null : "storage must not be null";
        assert taskList != null : "taskList must not be null";

        Task t = taskList.deleteTask(index);
        assert t != null : "task should exist for a valid index";
        this.task = t;
        taskList.deleteTask(index);
        storage.saveTasks(taskList.getListOfTasks());
        return ui.showDelete(t, taskList);
    }

    /**
     * Reverts this command by restoring the previously deleted task.
     *
     * @param taskList task list to operate on.
     * @throws AnoopException if the task cannot be restored.
     */
    @Override
    public void undo(TaskList taskList) throws AnoopException {
        taskList.store(this.task);
    }

    /**
     * Returns {@code true} because delete operations can be undone.
     *
     * @return {@code true}.
     */
    @Override
    public boolean isUndoable() {
        return true;
    }
}
