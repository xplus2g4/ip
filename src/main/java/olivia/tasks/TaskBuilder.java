package olivia.tasks;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents a task builder.
 */
public abstract class TaskBuilder<T extends TaskBuilder<T>> {
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
     * Constructs a task builder.
     *
     * @param type The type of task.
     */
    public TaskBuilder(String type) {
        this.type = type;
    }

    /**
     * Returns the task builder itself.
     *
     * @return The task builder.
     */
    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this; // Safe because T is the same type as the class.
    }

    /**
     * Sets the UUID of the task.
     *
     * @param id The UUID of the task.
     * @return The task builder.
     */
    public T withId(UUID id) {
        this.id = id;
        return self();
    }

    /**
     * Sets the description of the task.
     *
     * @param description The description of the task.
     * @return The task builder.
     */
    public T withDescription(String description) {
        this.description = description;
        return self();
    }

    /**
     * Marks the completion status of the task.
     *
     * @param isDone The completion status of the task.
     * @return
     */
    public T withIsDone(boolean isDone) {
        this.isDone = isDone;
        return self();
    }

    /**
     * Marks the task as done.
     *
     * @return The task builder.
     */
    public T markAsDone() {
        this.isDone = true;
        return self();
    }

    /**
     * Marks the task as undone.
     *
     * @return The task builder.
     */
    public T markAsUndone() {
        this.isDone = false;
        return self();
    }

    /**
     * Sets the previous task of the task.
     *
     * @param previousTask The previous task of the task.
     * @return The task builder.
     */
    public T withPreviousTask(Task previousTask) {
        this.previousTask = Optional.of(previousTask);
        return self();
    }

    /**
     * Sets the previous task of the task.
     *
     * @param previousTask The previous task of the task.
     * @return The task builder.
     */
    public T withPreviousTask(Optional<Task> previousTask) {
        this.previousTask = previousTask;
        return self();
    }

    public abstract Task build();
}
