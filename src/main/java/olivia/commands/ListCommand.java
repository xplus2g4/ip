package olivia.commands;

import olivia.Storage;
import olivia.Ui;
import olivia.tasks.TaskList;

public class ListCommand extends Command {
    public ListCommand(String rawCommand) {
        super(rawCommand);
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        ui.showTaskList(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
