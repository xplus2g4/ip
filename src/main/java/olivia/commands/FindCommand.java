package olivia.commands;

import olivia.Storage;
import olivia.Ui;
import olivia.tasks.TaskList;

public class FindCommand extends Command {
    public FindCommand(String rawCommand) {
        super(rawCommand);
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        String keyword = rawCommand.substring(5);
        TaskList matchingTasks = tasks.findTasks(keyword);
        ui.showFoundTasks(matchingTasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
