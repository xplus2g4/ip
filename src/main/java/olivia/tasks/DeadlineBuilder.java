package olivia.tasks;

import java.time.LocalDateTime;

/**
 * Represents a deadline builder.
 */
public class DeadlineBuilder extends TaskBuilder<DeadlineBuilder> {
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

    /**
     * Constructs a deadline builder.
     */
    public DeadlineBuilder() {
        super("D");
    }

    /**
     * Sets the deadline of the deadline.
     *
     * @param by The deadline.
     * @return The deadline builder.
     */
    public DeadlineBuilder withDeadline(LocalDateTime by) {
        this.by = by;
        return this;
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
