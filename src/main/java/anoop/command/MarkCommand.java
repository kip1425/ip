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

    /** Constructor for MarkCommand, includes which task to mark. */
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
        Task t = taskList.getTask(index);
        taskList.markTaskAsDone(index);
        storage.saveTasks(taskList.getListOfTasks());
        return ui.showMarked(t);
    }
}
