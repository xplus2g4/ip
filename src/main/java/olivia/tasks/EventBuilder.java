package olivia.tasks;

import java.time.LocalDateTime;

/**
 * Represents an event builder.
 */
public class EventBuilder extends TaskBuilder {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs an event builder.
     *
     * @param description The description of the event.
     * @param at The time of the event.
     */
    public EventBuilder(String description, LocalDateTime from, LocalDateTime to) {
        super("E", description);
        this.from = from;
        this.to = to;
    }

    @Override
    public Event build() {
        Event event = new Event(id, description, previousTask, from, to);

        if (isDone) {
            event.markAsDone();
        } else {
            event.markAsUndone();
        }
        return event;
    }
}
