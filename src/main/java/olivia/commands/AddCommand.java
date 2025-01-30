package olivia.commands;

import java.time.format.DateTimeParseException;
import java.util.Optional;

import olivia.OliviaException;
import olivia.Storage;
import olivia.Ui;
import olivia.tasks.Deadline;
import olivia.tasks.Event;
import olivia.tasks.Task;
import olivia.tasks.TaskList;
import olivia.tasks.Todo;

/**
 * Represents a command to mark a task as done in the task list.
 */
public class AddCommand extends Command {
    public AddCommand(String rawCommand) {
        super(rawCommand);
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) throws OliviaException {
        try {
            Task task = parseTask();
            tasks.addTask(task);
            ui.showTaskAdded(task, tasks.getSize());
            storage.saveTasks(tasks);
            storage.saveTasks(tasks);
        } catch (IllegalArgumentException e) {
            throw new OliviaException(e.getMessage());
        } catch (DateTimeParseException e) {
            throw new OliviaException(
                    "Please enter a valid date and time in the format: dd/MM/yyyy HH:mm");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    private Task parseTask() {
        Optional<? extends Task> task = Todo.isTodo(this.rawCommand);
        if (task.isPresent()) {
            return task.get();
        } else if ((task = Deadline.isDeadline(this.rawCommand)).isPresent()) {
            return task.get();
        } else if ((task = Event.isEvent(this.rawCommand)).isPresent()) {
            return task.get();
        } else {
            throw new IllegalArgumentException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
