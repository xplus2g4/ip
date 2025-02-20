package olivia.tasks;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents a todo task in the task list.
 */
public class Todo extends Task {
    protected Todo(UUID id, String description, Optional<Task> previousTask) {
        super(id, "T", description, previousTask);
    }

    @Override
    public String toCsvString() {
        return super.toCsvString();
    }

    @Override
    public Todo copy() {
        Todo todo = new Todo(UUID.randomUUID(), description, previousTask);
        if (isDone) {
            todo.markAsDone();
        } else {
            todo.markAsUndone();
        }
        return todo;
    }

    @Override
    public String toString() {
        String output = "[" + getStatusIcon() + "] Todo: " + description;
        if (previousTask.isPresent()) {
            return "(after: " + previousTask.get().toMiniString() + ") " + output;
        }
        return output;
    }
}
