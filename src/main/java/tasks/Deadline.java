package tasks;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadline extends Task {
    private String deadline;

    public static Optional<Task> isDeadline(String input) {
        Pattern pattern = Pattern.compile("deadline (.+) /by (.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Optional.of(new Deadline(matcher.group(1), matcher.group(2)));
        }
        return Optional.empty();
    }

    public Deadline(String description, String deadline) {
        super("D", description);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + deadline + ")";
    }
}
