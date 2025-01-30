package olivia.commands;

import olivia.OliviaException;
import olivia.Storage;
import olivia.tasks.Task;
import olivia.tasks.TaskList;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private Task task;

    public DeleteCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws OliviaException {
        try {
            tasks.removeTask(task);
        } catch (NumberFormatException e) {
            throw new OliviaException("The task number provided is invalid.");
        } catch (IndexOutOfBoundsException e) {
            throw new OliviaException("The task number provided is out of range.");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
