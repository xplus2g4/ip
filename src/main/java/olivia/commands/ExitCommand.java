package olivia.commands;


import olivia.Storage;
import olivia.tasks.TaskList;

/**
 * Represents a command to exit the Olivia chatbot.
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Storage storage) {
        // Do nothing
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
