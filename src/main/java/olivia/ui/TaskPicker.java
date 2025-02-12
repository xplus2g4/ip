package olivia.ui;

import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import olivia.tasks.Task;

/**
 * Represents a task picker UI component that allows users to select a task from a list.
 * The task picker uses a ComboBox to display the tasks and provides methods to set the tasks
 * and handle actions when a task is selected.
 */
public class TaskPicker {
    private ComboBox<Optional<Task>> taskPicker;

    static class TaskCell extends StringConverter<Optional<Task>> {
        @Override
        public String toString(Optional<Task> task) {
            return task != null ? task.map(Task::toMiniString).orElse("None") : "None";
        }

        @Override
        public Optional<Task> fromString(String string) {
            return null;
        }
    }

    /**
     * Constructs a task picker.
     *
     */
    public TaskPicker() {
        this.taskPicker = new ComboBox<>();

        this.taskPicker.setConverter(new TaskCell());
    }

    /**
     * Returns the task picker.
     *
     * @return The task picker.
     */
    public ComboBox<Optional<Task>> getTaskPicker() {
        return taskPicker;
    }

    /**
     * Sets the tasks to be displayed in the task picker.
     *
     * @param tasks The list of tasks to be displayed.
     */
    public void setTasks(List<Task> tasks) {
        this.taskPicker.getItems().clear();
        this.taskPicker.getItems().addAll(tasks.stream().map(Optional::of).toList());
        this.taskPicker.getItems().add(Optional.empty());
        this.taskPicker.setValue(Optional.empty());
    }

    /**
     * Sets the action handler for the task picker.
     *
     * @param handler The action handler.
     */
    public void setOnAction(EventHandler<ActionEvent> handler) {
        this.taskPicker.setOnAction(handler);
    }
}
