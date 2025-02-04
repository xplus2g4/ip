package olivia.tasks;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a task.
 */
public abstract class Task implements Comparable<Task> {
    public static final DateTimeFormatter WRITE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final DateTimeFormatter VIEW_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy");

    protected String type;
    protected String description;
    protected boolean isDone;
    protected Optional<Task> previousTask = Optional.empty();

    private UUID id = UUID.randomUUID();

    /**
     * Constructs a task.
     *
     * @param type The type of task.
     * @param description The description of the task.
     */
    protected Task(UUID id, String type, String description, Optional<Task> previousTask) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.isDone = false;
        this.previousTask = previousTask;
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

    public Optional<Task> getPreviousTask() {
        return previousTask;
    }

    /**
     * Returns the UUID of the task.
     *
     * @return The UUID of the task.
     */
    public String toCsvString() {
        String output = id + "|" + type + "|" + (isDone ? "1" : "0") + "|" + description;
        if (previousTask.isPresent()) {
            return output + "|" + previousTask.get().id;
        }
        return output + "|null";
    }

    public String toMiniString() {
        return description.toString();
    }

    @Override
    public String toString() {
        String output = "[" + getType() + "][" + getStatusIcon() + "] " + description;
        if (previousTask.isPresent()) {
            return "(after: " + previousTask.get().toMiniString() + ") " + output;
        }
        return output;
    }

    @Override
    public int compareTo(Task other) {
        if (this.previousTask.isPresent() && other.previousTask.isPresent()) {
            return this.id.compareTo(other.id);
        } else if (this.previousTask.isPresent()) {
            return 1;
        } else if (other.previousTask.isPresent()) {
            return -1;
        }
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

    public abstract Task copy();
}
