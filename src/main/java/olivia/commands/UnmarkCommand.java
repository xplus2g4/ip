package olivia.commands;

import olivia.OliviaException;
import olivia.Storage;
import olivia.Ui;
import olivia.tasks.Task;
import olivia.tasks.TaskList;

public class UnmarkCommand extends Command {
    public UnmarkCommand(String rawCommand) {
        super(rawCommand);
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) throws OliviaException {
        try {
            int index = Integer.parseInt(this.rawCommand.substring(7)) - 1;
            Task task = tasks.getTask(index);
            task.markAsUndone();
            ui.showTaskUnmarked(task);
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
