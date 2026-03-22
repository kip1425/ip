package anoop.command;

import anoop.Storage;
import anoop.Ui;
import anoop.task.TaskList;

/**
 * Represents a command to exit the application.
 */
public class ByeCommand extends Command {
    /**
     * Shows the goodbye message to the user.
     *
     * @param ui user interface to show bye message.
     * @param storage storage (not used for this command).
     * @param taskList task list (not used for this command).
     */
    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) {
        assert ui != null : "ui must not be null";

        return ui.showBye();
    }

    /**
     * Returns true to indicate this command should exit the application.
     *
     * @return true
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
