package olivia.commands;

import java.time.format.DateTimeParseException;

import olivia.OliviaException;
import olivia.storage.Storage;
import olivia.tasks.Task;
import olivia.tasks.TaskList;

/**
 * Represents a command to mark a task as done in the task list.
 */
public class AddCommand extends Command {
    private Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws OliviaException {
        try {
            tasks.addTask(this.task);
            storage.saveTasks(tasks);
        } catch (IllegalArgumentException e) {
            throw new OliviaException(e.getMessage());
        } catch (DateTimeParseException e) {
            throw new OliviaException(
                    "Please enter a valid date and time in the format: dd/MM/yyyy HH:mm");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
