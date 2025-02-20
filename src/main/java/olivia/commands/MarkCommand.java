package olivia.commands;

import olivia.OliviaException;
import olivia.storage.Storage;
import olivia.tasks.Task;
import olivia.tasks.TaskList;

/**
 * Represents a command to mark a task as done in the task list.
 */
public class MarkCommand extends Command {
    private Task task;

    public MarkCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws OliviaException {
        try {
            this.task.markAsDone();
            tasks.getTasksView().refresh();
            storage.saveTasks(tasks);
        } catch (NumberFormatException e) {
            throw new OliviaException("The task index must be a number.");
        } catch (IndexOutOfBoundsException e) {
            throw new OliviaException("The task index is out of range.");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
