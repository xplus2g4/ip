package olivia.tasks;

import java.time.LocalDateTime;

/**
 * Represents a deadline builder.
 */
public class DeadlineBuilder extends TaskBuilder {
    private LocalDateTime by;

    /**
     * Constructs a deadline builder.
     *
     * @param description The description of the deadline.
     * @param by The deadline.
     */
    public DeadlineBuilder(String description, LocalDateTime by) {
        super("D", description);
        this.by = by;
    }

    @Override
    public Deadline build() {
        Deadline deadline = new Deadline(id, description, previousTask, by);

        if (isDone) {
            deadline.markAsDone();
        } else {
            deadline.markAsUndone();
        }
        return deadline;
    }
}
