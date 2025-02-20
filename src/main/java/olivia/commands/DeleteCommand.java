package olivia.commands;

import java.util.List;
import java.util.Optional;

import olivia.OliviaException;
import olivia.storage.Storage;
import olivia.tasks.Task;
import olivia.tasks.TaskList;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private Task deletingTask;

    public DeleteCommand(Task deletingTask) {
        this.deletingTask = deletingTask;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws OliviaException {
        try {
            Optional<Task> optionalTask = Optional.of(deletingTask);
            List<Task> dependencies = tasks.getTasks().stream()
                    .filter(task -> task.getPreviousTask().equals(optionalTask)).toList();
            if (!dependencies.isEmpty()) {
                handleCommandError(new OliviaException(
                        "The task cannot be deleted as it has dependent tasks."));
                return;
            }

            tasks.removeTask(deletingTask);
            storage.saveTasks(tasks);
        } catch (NumberFormatException e) {
            handleCommandError(new OliviaException("The task number provided is invalid."));
        } catch (IndexOutOfBoundsException e) {
            handleCommandError(new OliviaException("The task number provided is out of range."));
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
