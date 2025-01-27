package tasks;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event extends Task {
    private String startTime;
    private String endTime;

    public static Optional<Event> isEvent(String input) {
        if (!input.startsWith("event")) {
            return Optional.empty();
        }
        Pattern pattern = Pattern.compile("event (.+) /from (.+) /to (.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Optional.of(new Event(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        throw new IllegalArgumentException("Invalid event format. Use 'event <description> /from <start time> /to <end time>'");
    }

    public Event(String description, String startTime, String endTime) {
        super("E", description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDuration() {
        return "from: " + startTime + " to " + endTime;
    }
    @Override
    public String toString() {
        return super.toString() + " (" + getDuration() + ")";
    }

    @Override
    public String toCsvString() {
        return "E|" + (isDone() ? "1" : "0") + "|" + getDescription() + "|" + startTime + "|" + endTime;
    }
}
