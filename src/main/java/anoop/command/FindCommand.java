package anoop.command;

import anoop.Storage;
import anoop.Ui;
import anoop.exception.AnoopException;
import anoop.task.TaskList;

/**
 * Finds tasks whose descriptions contain a given keyword and displays them to the user.
 */
public class FindCommand extends Command {
    /** Keyword used to match tasks. */
    private final String keyword;

    /**
     * Creates a command that searches for tasks containing the given keyword.
     *
     * @param keyword Keyword to match within task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Searches the task list for matching tasks and shows them via the UI.
     *
     * @param ui UI used to display results.
     * @param storage storage used by the application (unused here).
     * @param taskList task list to search.
     */
    @Override
    public void execute(Ui ui, Storage storage, TaskList taskList) {
        TaskList temp = taskList.find(this.keyword);
        ui.showFind(temp);
    }
}
