package olivia.tasks;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a todo task in the task list.
 */
public class Todo extends Task {
    public Todo(String description) {
        super("T", description);
    }

    /**
     * Parses the input to check if it is a valid todo task.
     *
     * @param input The input to be parsed.
     * @return An optional containing the todo task if the input is valid, empty otherwise.
     */
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

    @Override
    public String toCsvString() {
        return "T|" + (isDone() ? "1" : "0") + "|" + getDescription();
    }
}
