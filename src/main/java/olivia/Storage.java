package olivia;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import olivia.tasks.DeadlineBuilder;
import olivia.tasks.EventBuilder;
import olivia.tasks.Task;
import olivia.tasks.TaskBuilder;
import olivia.tasks.TaskList;
import olivia.tasks.TodoBuilder;

/**
 * Represents a storage class to read and write tasks to a file.
 */
public class Storage {
    private Path filePath;

    /**
     * Constructs a storage class.
     *
     * @param filePath The path to the file to store tasks.
     */
    public Storage(String... filePath) {
        this.filePath = Paths.get("", filePath);
    }

    /**
     * Reads tasks from the file.
     *
     * @return The list of tasks read from the file.
     * @throws OliviaException If there is an error reading data from the file.
     */
    public List<Task> readTasks() throws OliviaException {
        try {
            File file = this.filePath.toFile();
            if (!file.exists()) {
                return new ArrayList<>();
            }
            Map<UUID, Task> taskMap = new HashMap<>();

            return Files.lines(this.filePath)
                .map(line -> fromString(taskMap, line))
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new OliviaException("Error reading data from file");
        }
    }

    /**
     * Saves tasks to the file.
     *
     * @param tasks The list of tasks to save.
     * @throws OliviaException If there is an error writing data to the file.
     */
    public void saveTasks(TaskList tasks) throws OliviaException {
        try {
            if (!this.filePath.toFile().exists()) {
                Files.createDirectories(this.filePath.getParent());
                Files.createFile(this.filePath);
            }
            Files.write(this.filePath, tasks.getTasksCsv());
        } catch (Exception e) {
            e.printStackTrace();
            throw new OliviaException("Error writing data to file");
        }
    }

    /**
     * Converts a string to a task.
     *
     * @param input The string to convert.
     * @return The task converted from the string.
     */
    public static Task fromString(Map<UUID, Task> loadedTasks, String input) {
        String[] parts = input.split("\\|");
        assert parts.length >= 4 : "Invalid task format";

        UUID id = UUID.fromString(parts[0]);
        String type = parts[1];
        boolean isDone = parts[2].equals("1");
        String description = parts[3];
        String previousTaskId = parts[4];
        Optional<Task> previousTask = Optional.empty();
        if (!previousTaskId.equals("null")) {
            Task task = loadedTasks.get(UUID.fromString(previousTaskId));
            assert task != null : "Invalid previous task ID";
            previousTask = Optional.of(task);
        }
        TaskBuilder taskBuilder = null;
        switch (type) {
        case "T":
            taskBuilder = new TodoBuilder(description);
            break;
        case "D":
            LocalDateTime deadline = LocalDateTime.parse(parts[5], Task.WRITE_FORMATTER);
            taskBuilder = new DeadlineBuilder(description, deadline);
            break;
        case "E":
            LocalDateTime startTime = LocalDateTime.parse(parts[5], Task.WRITE_FORMATTER);
            LocalDateTime endTime = LocalDateTime.parse(parts[6], Task.WRITE_FORMATTER);
            taskBuilder = new EventBuilder(description, startTime, endTime);
            break;
        default:
            throw new OliviaException("Invalid task type in file");
        }
        Task task =
                taskBuilder.withId(id).withIsDone(isDone).withPreviousTask(previousTask).build();
        loadedTasks.put(id, task);
        return task;
    }
}
