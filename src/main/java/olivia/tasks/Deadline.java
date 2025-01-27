package olivia.tasks;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadline extends Task {
    private LocalDateTime deadline;

    public static Optional<Task> isDeadline(String input) {
        if (!input.startsWith("deadline")) {
            return Optional.empty();
        }
        Pattern pattern = Pattern.compile("deadline (.+) /by (.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Optional.of(new Deadline(matcher.group(1), LocalDateTime.parse(matcher.group(2), Task.formatter)));
        }
        throw new IllegalArgumentException(
                "Invalid deadline format. Use 'deadline <description> /by <dd/MM/yyyy HH:mm>'");
    }

    public Deadline(String description, LocalDateTime deadline) {
        super("D", description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadline.format(formatter) + ")";
    }

    @Override
    public String toCsvString() {
        return "D|" + (isDone() ? "1" : "0") + "|" + getDescription() + "|" + deadline.format(formatter);
    }
}
