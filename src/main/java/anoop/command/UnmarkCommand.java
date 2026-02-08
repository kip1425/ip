package anoop.command;

import anoop.Storage;
import anoop.Ui;
import anoop.exception.AnoopException;
import anoop.task.Task;
import anoop.task.TaskList;

/**
 * Represents a command to unmark a task in the task list.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /** Constructor for UnmarkCommand, includes which task to unmark. */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Marks the task as not done and saves the updated list to the storage.
     *
     * @param ui user interface to show task unmarked message.
     * @param storage storage to save the updated task list.
     * @param taskList task list containing the task to unmark.
     * @return response string for the UI to display.
     * @throws AnoopException when index is not valid or saving fails.
     */
    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) throws AnoopException {
        Task t = taskList.getTask(index);
        taskList.markTaskAsNotDone(index);
        storage.saveTasks(taskList.getListOfTasks());
        return ui.showUnmarked(t);
    }
}
