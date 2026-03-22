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
     * @return response string for the UI to display.
     */
    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) {
        assert ui != null : "ui must not be null";
        assert taskList != null : "taskList must not be null";

        return ui.showList(taskList);
    }
}
