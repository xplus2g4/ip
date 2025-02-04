package olivia.tasks;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents a task builder.
 */
public abstract class TaskBuilder {
    protected String type;
    protected String description;

    protected UUID id = UUID.randomUUID();
    protected boolean isDone = false;
    protected Optional<Task> previousTask = Optional.empty();

    /**
     * Constructs a task builder.
     *
     * @param type The type of task.
     * @param description The description of the task.
     */
    public TaskBuilder(String type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * Sets the UUID of the task.
     *
     * @param id The UUID of the task.
     * @return The task builder.
     */
    public TaskBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * Marks the completion status of the task.
     *
     * @param isDone The completion status of the task.
     * @return
     */
    public TaskBuilder withIsDone(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    /**
     * Marks the task as done.
     *
     * @return The task builder.
     */
    public TaskBuilder markAsDone() {
        this.isDone = true;
        return this;
    }

    /**
     * Marks the task as undone.
     *
     * @return The task builder.
     */
    public TaskBuilder markAsUndone() {
        this.isDone = false;
        return this;
    }

    /**
     * Sets the previous task of the task.
     *
     * @param previousTask The previous task of the task.
     * @return The task builder.
     */
    public TaskBuilder withPreviousTask(Task previousTask) {
        this.previousTask = Optional.of(previousTask);
        return this;
    }

    /**
     * Sets the previous task of the task.
     *
     * @param previousTask The previous task of the task.
     * @return The task builder.
     */
    public TaskBuilder withPreviousTask(Optional<Task> previousTask) {
        this.previousTask = previousTask;
        return this;
    }

    public abstract Task build();
}
