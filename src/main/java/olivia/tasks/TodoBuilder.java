package olivia.tasks;

/**
 * Represents a todo builder.
 */
public class TodoBuilder extends TaskBuilder<TodoBuilder> {
    /**
     * Constructs a todo builder.
     *
     * @param description The description of the todo.
     */
    public TodoBuilder(String description) {
        super("T", description);
    }

    /**
     * Constructs a todo builder.
     */
    public TodoBuilder() {
        super("T");
    }

    @Override
    public Todo build() {
        Todo todo = new Todo(id, description, previousTask);

        if (isDone) {
            todo.markAsDone();
        } else {
            todo.markAsUndone();
        }
        return todo;
    }
}
