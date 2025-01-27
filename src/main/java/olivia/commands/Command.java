package olivia.commands;

import olivia.OliviaException;
import olivia.Storage;
import olivia.Ui;
import olivia.tasks.TaskList;

public abstract class Command {
    String rawCommand;

    public Command(String rawCommand) {
        this.rawCommand = rawCommand;
    }

    public abstract void execute(TaskList tasks, Storage storage, Ui ui) throws OliviaException;

    public abstract boolean isExit();
}
