package olivia.tasks;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a deadline task in the task list.
 */
public class Deadline extends Task {
    private LocalDateTime deadline;

    /**
     * Creates a deadline task with the specified description and deadline.
     *
     * @param description The description of the deadline task.
     * @param deadline The deadline of the deadline task.
     */
    public Deadline(String description, LocalDateTime deadline) {
        super("D", description);
        this.deadline = deadline;
    }

    /**
     * Parses the input to check if it is a valid deadline task.
     *
     * @param input The input to be parsed.
     * @return An optional containing the deadline task if the input is valid, empty otherwise.
     */
    public static Optional<Task> isDeadline(String input) {
        if (!input.startsWith("deadline")) {
            return Optional.empty();
        }
        Pattern pattern = Pattern.compile("deadline (.+) /by (.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Optional.of(new Deadline(matcher.group(1),
                    LocalDateTime.parse(matcher.group(2), Task.WRITE_FORMATTER)));
        }
        throw new IllegalArgumentException(
                "Invalid deadline format. Use 'deadline <description> /by <dd/MM/yyyy HH:mm>'");
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadline.format(VIEW_FORMATTER) + ")";
    }

    @Override
    public String toCsvString() {
        return "D|" + (isDone() ? "1" : "0") + "|" + getDescription() + "|"
                + deadline.format(WRITE_FORMATTER);
    }
}
