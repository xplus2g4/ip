package tasks;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event extends Task {
    private String startTime;
    private String endTime;

    public static Optional<Event> isEvent(String input) {
        Pattern pattern = Pattern.compile("event (.+) /from (.+) /to (.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Optional.of(new Event(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        return Optional.empty();
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
}
