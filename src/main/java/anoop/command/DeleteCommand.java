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
    private final int index;

    /** Constructor for DeleteCommand, includes which task to delete. */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Deletes the task from the task list and saves the updated list to the storage.
     *
     * @param ui user interface to show task deleted message.
     * @param storage storage to save the updated task list.
     * @param taskList task list containing the task to delete.
     * @throws AnoopException when index is not valid or saving fails.
     */
    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) throws AnoopException {
        Task t = taskList.getTask(index);
        taskList.deleteTask(index);
        storage.saveTasks(taskList.getListOfTasks());
        ui.showDelete(t, taskList);
    }
}
