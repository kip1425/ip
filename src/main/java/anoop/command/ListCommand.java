package anoop.command;

import anoop.Storage;
import anoop.Ui;
import anoop.task.TaskList;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {
    /**
     * Shows the list of tasks to the user.
     *
     * @param ui user interface to show list message.
     * @param storage storage (not used for this command).
     * @param taskList task list to show.
     */
    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        ui.showList(taskList);
    }
}
