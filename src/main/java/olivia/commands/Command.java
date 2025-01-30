package olivia.commands;

import olivia.OliviaException;
import olivia.Storage;
import olivia.tasks.TaskList;

/**
 * Represents a command that can be executed by Olivia.
 */
public abstract class Command {
    /**
     * Executes the command.
     *
     * @param tasks The list of tasks.
     * @param storage The storage to store tasks.
     * @throws OliviaException If an error occurs during execution.
     */
    public abstract void execute(TaskList tasks, Storage storage) throws OliviaException;

    /**
     * Returns true if the command is an exit command.
     *
     * @return True if the command is an exit command.
     */
    public abstract boolean isExit();
}
