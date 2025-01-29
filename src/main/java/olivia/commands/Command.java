package olivia.commands;

import olivia.OliviaException;
import olivia.Storage;
import olivia.Ui;
import olivia.tasks.TaskList;

/**
 * Represents a command that can be executed by Olivia.
 */
public abstract class Command {
    String rawCommand;

    /**
     * Constructs a command.
     *
     * @param rawCommand The raw command string.
     */
    public Command(String rawCommand) {
        this.rawCommand = rawCommand;
    }

    /**
     * Executes the command.
     *
     * @param tasks The list of tasks.
     * @param storage The storage to store tasks.
     * @param ui The user interface.
     * @throws OliviaException If an error occurs during execution.
     */
    public abstract void execute(TaskList tasks, Storage storage, Ui ui) throws OliviaException;

    /**
     * Returns true if the command is an exit command.
     *
     * @return True if the command is an exit command.
     */
    public abstract boolean isExit();
}
