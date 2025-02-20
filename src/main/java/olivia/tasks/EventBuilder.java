package olivia.tasks;

import java.time.LocalDateTime;

/**
 * Represents an event builder.
 */
public class EventBuilder extends TaskBuilder<EventBuilder> {
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

    /**
     * Constructs an event builder.
     */
    public EventBuilder() {
        super("E");
    }

    /**
     * Sets the time of the event.
     *
     * @param from The start time of the event.
     * @return The event builder.
     */
    public EventBuilder withStartTime(LocalDateTime from) {
        this.from = from;
        return this;
    }

    /**
     * Sets the time of the event.
     *
     * @param to The end time of the event.
     * @return The event builder.
     */
    public EventBuilder withEndTime(LocalDateTime to) {
        this.to = to;
        return this;
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
