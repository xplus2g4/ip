package olivia.commands;

import olivia.OliviaException;
import olivia.Storage;
import olivia.Ui;
import olivia.tasks.Task;
import olivia.tasks.TaskList;

/**
 * Represents a command to mark a task as done in the task list.
 */
public class MarkCommand extends Command {
    public MarkCommand(String rawCommand) {
        super(rawCommand);
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) throws OliviaException {
        try {
            int index = Integer.parseInt(this.rawCommand.substring(5)) - 1;
            Task task = tasks.getTask(index);
            task.markAsDone();
            ui.showTaskMarked(task);
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
