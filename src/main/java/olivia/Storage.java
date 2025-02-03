package olivia;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import olivia.tasks.Deadline;
import olivia.tasks.Event;
import olivia.tasks.Task;
import olivia.tasks.TaskList;
import olivia.tasks.Todo;

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
            return Files.lines(this.filePath).map(Storage::fromString).collect(Collectors.toList());
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
    public static Task fromString(String input) {
        String[] parts = input.split("\\|");
        assert parts.length >= 3 : "Invalid task format";

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task = null;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            task = new Deadline(description, LocalDateTime.parse(parts[3], Task.WRITE_FORMATTER));
            break;
        case "E":
            task = new Event(description, LocalDateTime.parse(parts[3], Task.WRITE_FORMATTER),
                    LocalDateTime.parse(parts[4], Task.WRITE_FORMATTER));
            break;
        default:
            throw new OliviaException("Invalid task type in file");
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
