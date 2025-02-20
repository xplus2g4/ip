package olivia.storage;

import java.util.List;

import olivia.OliviaException;
import olivia.tasks.Task;
import olivia.tasks.TaskList;

/**
 * Represents a storage class to read and write tasks to a local storage.
 */
public abstract class Storage {
    /**
     * Reads tasks from the storage.
     *
     * @return The list of tasks read from the storage.
     * @throws OliviaException If there is an error reading data from the storage.
     */
    public abstract List<Task> readTasks() throws OliviaException;

    /**
     * Saves tasks to the storage.
     *
     * @param tasks The list of tasks to save.
     * @throws OliviaException If there is an error writing data to the storage.
     */
    public abstract void saveTasks(TaskList tasks) throws OliviaException;
}
