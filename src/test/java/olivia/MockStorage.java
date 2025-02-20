package olivia;

import java.util.List;
import java.util.UUID;

import olivia.storage.Storage;
import olivia.tasks.Task;
import olivia.tasks.TaskList;
import olivia.tasks.TodoBuilder;

/**
 * Represents a mock storage class to read and write tasks to a mock storage.
 */
public class MockStorage extends Storage {
    private List<Task> tasks;

    /**
     * Constructs a mock storage with dummy tasks.
     */
    public MockStorage() {
        this.tasks = List.of(
            new TodoBuilder("todo 1")
                .withId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .withIsDone(false)
                .build()
        );
    }

    /**
     * Reads dummy tasks from the mock storage.
     *
     * @return The list of tasks read from the mock storage.
     * @throws OliviaException If there is an error reading data from the mock storage.
     */
    @Override
    public List<Task> readTasks() throws OliviaException {
        return tasks.stream().map(Task::copy).toList();
    }

    @Override
    public void saveTasks(TaskList tasks) throws OliviaException {
        this.tasks = tasks.getTasks().stream().map(Task::copy).toList();
    }
}
