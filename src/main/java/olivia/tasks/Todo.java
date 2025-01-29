package olivia.tasks;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Todo extends Task {
    public static Optional<Todo> isTodo(String input) {
        if (!input.startsWith("todo")) {
            return Optional.empty();
        }

        Pattern pattern = Pattern.compile("todo (.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Optional.of(new Todo(matcher.group(1)));
        }
        throw new IllegalArgumentException("Invalid todo format. Use 'todo <description>'");
    }

    public Todo(String description) {
        super("T", description);
    }

    @Override
    public String toCsvString() {
        return "T|" + (isDone() ? "1" : "0") + "|" + getDescription();
    }
}
