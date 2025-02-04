package olivia.tasks;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents an event task in the task list.
 */
public class Event extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /**
     * Constructs an event.
     *
     * @param id The UUID of the event.
     * @param description The description of the event.
     * @param previousTask The previous task of the event.
     * @param startTime The start time of the event.
     * @param endTime The end time of the event.
     */
    public Event(UUID id, String description, Optional<Task> previousTask,
            LocalDateTime startTime, LocalDateTime endTime) {
        super(id, "E", description, previousTask);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDuration() {
        return "from: " + startTime.format(VIEW_FORMATTER) + " to "
                + endTime.format(VIEW_FORMATTER);
    }

    @Override
    public String toString() {
        return super.toString() + " (" + getDuration() + ")";
    }

    @Override
    public String toCsvString() {
        return super.toCsvString() + "|" + startTime.format(WRITE_FORMATTER) + "|"
                + endTime.format(WRITE_FORMATTER);
    }

    @Override
    public Event copy() {
        Event event = new Event(UUID.randomUUID(), description, previousTask, startTime, endTime);
        if (isDone) {
            event.markAsDone();
        } else {
            event.markAsUndone();
        }
        return event;
    }
}
