package olivia.tasks;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Represents a task.
 */
public abstract class Task implements Comparable<Task> {
    public static final DateTimeFormatter WRITE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final DateTimeFormatter VIEW_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy");
    private UUID id;
    private String type;
    private String description;
    private boolean isDone;

    /**
     * Constructs a task.
     *
     * @param type The type of task.
     * @param description The description of the task.
     */
    public Task(String type, String description) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as undone.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns true if the task is done.
     *
     * @return True if the task is done.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return The status icon of the task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the type of the task.
     *
     * @return The type of the task.
     */
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "[" + getType() + "][" + getStatusIcon() + "] " + description;
    }

    @Override
    public int compareTo(Task other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task) {
            Task other = (Task) obj;
            return this.id.equals(other.id);
        }
        return false;
    }

    /**
     * Returns the task in CSV format.
     *
     * @return The task in CSV format.
     */
    public abstract String toCsvString();
}
