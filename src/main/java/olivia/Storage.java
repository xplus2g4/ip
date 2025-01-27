package olivia;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import olivia.tasks.Deadline;
import olivia.tasks.Event;
import olivia.tasks.Task;
import olivia.tasks.TaskList;
import olivia.tasks.Todo;

public class Storage {
    private Path filePath;

    public Storage(Path filePath) {
        this.filePath = filePath;
    }

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

    public static Task fromString(String input) {
        String[] parts = input.split("\\|");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task = null;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                task = new Deadline(description, LocalDateTime.parse(parts[3], Task.formatter));
                break;
            case "E":
                task = new Event(description, LocalDateTime.parse(parts[3], Task.formatter), LocalDateTime.parse(parts[4], Task.formatter));
                break;
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
