package olivia.tasks;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a deadline task in the task list.
 */
public class Deadline extends Task {
    private LocalDateTime deadline;

    /**
     * Constructs a deadline.
     *
     * @param id The UUID of the deadline.
     * @param description The description of the deadline.
     * @param deadline The deadline of the deadline.
     * @param previousTask The previous task of the deadline.
     */
    protected Deadline(UUID id, String description, Optional<Task> previousTask,
            LocalDateTime deadline) {
        super(id, "D", description, previousTask);
        this.deadline = deadline;
    }

    /**
     * Returns the deadline of the deadline.
     *
     * @return The deadline of the deadline.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        String output = "[" + getStatusIcon() + "] Deadline: " + description;
        if (previousTask.isPresent()) {
            output = "(after: " + previousTask.get().toMiniString() + ") " + output;
        }
        return output + " (by: " + deadline.format(VIEW_FORMATTER) + ")";
    }

    @Override
    public String toCsvString() {
        return super.toCsvString() + "|" + deadline.format(WRITE_FORMATTER);
    }

    @Override
    public Deadline copy() {
        Deadline deadline =
                new Deadline(UUID.randomUUID(), description, previousTask, this.deadline);
        if (isDone) {
            deadline.markAsDone();
        } else {
            deadline.markAsUndone();
        }
        return deadline;
    }
}
