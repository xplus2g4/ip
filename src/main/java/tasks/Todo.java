package tasks;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Todo extends Task {
    public static Optional<Todo> isTodo (String input) {
        Pattern pattern = Pattern.compile("todo (.+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return Optional.of(new Todo(matcher.group(1)));
        }
        return Optional.empty();
    }

    public Todo(String description) {
        super("T", description);
    }
}
