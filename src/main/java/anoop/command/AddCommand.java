package anoop.command;

import anoop.Storage;
import anoop.Ui;
import anoop.exception.AnoopException;
import anoop.task.Task;
import anoop.task.TaskList;

/**
 * Represents a command to add tasks to the task list.
 */
public class AddCommand extends Command {
    private final Task task;
    private int index;

    /**
     * Creates an add command for the given task.
     *
     * @param task task to add.
     */
    public AddCommand(Task task) {
        assert task != null : "task must not be null";

        this.task = task;
    }

    /**
     * Adds the task to the task list and saves the updated list to the storage.
     *
     * @param ui user interface to show task added message.
     * @param storage storage to save the updated task list.
     * @param taskList task list to add the task to.
     * @return response string for the UI to display.
     * @throws AnoopException if list is already full or saving fails.
     */
    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) throws AnoopException {
        assert ui != null : "ui must not be null";
        assert storage != null : "storage must not be null";
        assert taskList != null : "taskList must not be null";

        taskList.store(this.task);
        this.index = taskList.getCurrentSize();
        storage.saveTasks(taskList.getListOfTasks());

        return ui.showTaskAdded(this.task, taskList);
    }

    /**
     * Removes the task that was added by this command.
     *
     * @param taskList task list to operate on.
     * @throws AnoopException if the task cannot be removed.
     */
    @Override
    public void undo(TaskList taskList) throws AnoopException {
        taskList.deleteTask(this.index);
    }

    /**
     * Returns {@code true} because add operations can be undone.
     *
     * @return {@code true}.
     */
    @Override
    public boolean isUndoable() {
        return true;
    }
}
