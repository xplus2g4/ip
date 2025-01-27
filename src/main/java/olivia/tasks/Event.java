package olivia.tasks;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static Optional<Event> isEvent(String input) {
        if (!input.startsWith("event")) {
            return Optional.empty();
        }
        Pattern pattern = Pattern.compile("event (.+) /from (.+) /to (.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Optional.of(
                    new Event(matcher.group(1),
                            LocalDateTime.parse(matcher.group(2), Task.formatter),
                            LocalDateTime.parse(matcher.group(3), Task.formatter)));
        }
        throw new IllegalArgumentException(
                "Invalid event format. Use 'event <description> /from <dd/MM/yyyy HH:mm> /to <dd/MM/yyyy HH:mm>'");
    }

    public Event(String description, LocalDateTime startTime, LocalDateTime endTime) {
        super("E", description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDuration() {
        return "from: " + startTime.format(formatter) + " to " + endTime.format(formatter);
    }

    @Override
    public String toString() {
        return super.toString() + " (" + getDuration() + ")";
    }

    @Override
    public String toCsvString() {
        return "E|" + (isDone() ? "1" : "0") + "|" + getDescription() + "|" + startTime.format(formatter) + "|" + endTime.format(formatter);
    }
}
