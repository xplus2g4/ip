package olivia.tasks;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private ListView<Task> tasksView;
    private List<Task> tasks;

    /**
     * Constructs a task list.
     *
     * @param tasks The list of tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
        this.tasksView = new ListView<>();
        this.tasksView.getItems().addAll(tasks);

        this.tasksView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
            @Override
            public ListCell<Task> call(ListView<Task> listView) {
                return new ListCell<Task>() {
                    @Override
                    protected void updateItem(Task task, boolean empty) {
                        super.updateItem(task, empty);
                        if (empty || task == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            HBox cellLayout = new HBox(10);
                            Label taskLabel = new Label(task.toString());
                            if (task.isDone()) {
                                taskLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                            }
                            cellLayout.getChildren().add(taskLabel);
                            setGraphic(cellLayout);
                        }
                    }
                };
            }
        });
    }

    /**
     * Returns the list view of tasks.
     *
     * @return The list view of tasks.
     */
    public ListView<Task> getTasksView() {
        return tasksView;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasksView.getItems().add(task);
        tasks.add(task);
    }

    /**
     * Removes the task at the specified index.
     *
     * @param index The index of the task to remove.
     */
    public void removeTask(int index) {
        tasksView.getItems().remove(index);
        tasks.remove(index);
    }

    /**
     * Removes the specified task.
     *
     * @param task The task to remove.
     */
    public void removeTask(Task task) {
        tasksView.getItems().remove(task);
        tasks.remove(task);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index The index of the task.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the task at the specified index in the view.
     *
     * @param index The index of the task.
     * @return The task at the specified index in the view.
     */
    public Task getTaskInView(int index) {
        return tasksView.getItems().get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks in the list.
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Returns a list of tasks in CSV format.
     *
     * @return The list of tasks in CSV format.
     */
    public List<String> getTasksCsv() {
        return tasks.stream().map(Task::toCsvString).collect(Collectors.toList());
    }

    /**
     * Finds tasks that contain the keyword.
     *
     * @param keyword The keyword to search for.
     */
    public void findTasks(String keyword) {
        tasksView.getItems().clear();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                tasksView.getItems().add(task);
            }
        }
    }
}
