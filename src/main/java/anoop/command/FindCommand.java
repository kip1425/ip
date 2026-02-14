package anoop.command;

import anoop.Storage;
import anoop.Ui;
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
     * @return response string for the UI to display.
     */
    @Override
    public String execute(Ui ui, Storage storage, TaskList taskList) {
        return ui.showFind(taskList.find(this.keyword));
    }
}
