package olivia.commands;

import olivia.OliviaException;
import olivia.Storage;
import olivia.Ui;
import olivia.tasks.Task;
import olivia.tasks.TaskList;

public class DeleteCommand extends Command {
    public DeleteCommand(String rawCommand) {
        super(rawCommand);
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) throws OliviaException {
        try {
            int index = Integer.parseInt(this.rawCommand.substring(7)) - 1;
            Task task = tasks.getTask(index);
            tasks.removeTask(index);
            ui.showTaskDeleted(task, tasks.getSize());
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
