package olivia.commands;


import olivia.Storage;
import olivia.Ui;
import olivia.tasks.TaskList;

public class ExitCommand extends Command {
    public ExitCommand(String rawCommand) {
        super(rawCommand);
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
