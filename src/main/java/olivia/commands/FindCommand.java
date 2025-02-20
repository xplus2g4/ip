package olivia.commands;

import olivia.storage.Storage;
import olivia.tasks.TaskList;

/**
 * Represents a command to find tasks in the task list.
 */
public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) {
        tasks.findTasks(keyword);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
