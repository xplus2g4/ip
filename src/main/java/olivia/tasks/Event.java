package olivia.tasks;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an event task in the task list.
 */
public class Event extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    /**
     * Constructs an event.
     *
     * @param description The description of the event.
     * @param startTime The start time of the event.
     * @param endTime The end time of the event.
     */
    public Event(String description, LocalDateTime startTime, LocalDateTime endTime) {
        super("E", description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Parses the input to check if it is a valid event.
     *
     * @param input The input to be parsed.
     * @return An optional containing the event if the input is valid, empty otherwise.
     */
    public static Optional<Event> isEvent(String input) {
        if (!input.startsWith("event")) {
            return Optional.empty();
        }
        Pattern pattern = Pattern.compile("event (.+) /from (.+) /to (.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Optional.of(new Event(matcher.group(1),
                    LocalDateTime.parse(matcher.group(2), Task.WRITE_FORMATTER),
                    LocalDateTime.parse(matcher.group(3), Task.WRITE_FORMATTER)));
        }
        throw new IllegalArgumentException(
                "Invalid event format. Use 'event <description> /from <dd/MM/yyyy HH:mm> /to <dd/MM/yyyy HH:mm>'");
    }

    public String getDuration() {
        return "from: " + startTime.format(VIEW_FORMATTER) + " to " + endTime.format(VIEW_FORMATTER);
    }

    @Override
    public String toString() {
        return super.toString() + " (" + getDuration() + ")";
    }

    @Override
    public String toCsvString() {
        return "E|" + (isDone() ? "1" : "0") + "|" + getDescription() + "|"
                + startTime.format(WRITE_FORMATTER) + "|" + endTime.format(WRITE_FORMATTER);
    }
}
