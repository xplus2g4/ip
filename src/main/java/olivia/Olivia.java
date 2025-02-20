package olivia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import olivia.commands.AddCommand;
import olivia.commands.DeleteCommand;
import olivia.commands.ExitCommand;
import olivia.commands.MarkCommand;
import olivia.commands.UnmarkCommand;
import olivia.storage.FileStorage;
import olivia.storage.Storage;
import olivia.tasks.DeadlineBuilder;
import olivia.tasks.EventBuilder;
import olivia.tasks.Task;
import olivia.tasks.TaskBuilder;
import olivia.tasks.TaskList;
import olivia.tasks.TodoBuilder;
import olivia.ui.TaskPicker;

/**
 * Represents the main Olivia chatbot class.
 */
public class Olivia extends Application {
    private static final String[] PATH = {"data", "olivia.csv"};
    private Storage storage;
    private TaskList taskList;

    /**
     * Creates a new Olivia chatbot.
     */
    public Olivia() {
        this.storage = new FileStorage(PATH);

        try {
            this.taskList = new TaskList(storage.readTasks());
        } catch (OliviaException e) {
            taskList = new TaskList(new ArrayList<>());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Label taskTypeLabel = new Label("Task Type: ");
        ComboBox<String> taskType = new ComboBox<>();
        taskType.getItems().addAll("Todo", "Deadline", "Event");
        taskType.setValue("Todo");
        VBox labelledTaskType = new VBox(taskTypeLabel, taskType);

        TaskPicker taskPicker = new TaskPicker();
        taskPicker.setTasks(taskList.getTasks());

        Label taskLabel = new Label("Description: ");
        TextField taskInput = new TextField();
        VBox labelledTaskInput = new VBox(taskLabel, taskInput);

        Label deadlineLabel = new Label("By: ");
        DatePicker deadlinePicker = new DatePicker();
        VBox labelledDeadlinePicker = new VBox(deadlineLabel, deadlinePicker);

        Label eventStartLabel = new Label("Start: ");
        Label eventEndLabel = new Label("End: ");
        DatePicker eventStartPicker = new DatePicker();
        DatePicker eventEndPicker = new DatePicker();
        VBox labelledEventStartPicker = new VBox(eventStartLabel, eventStartPicker);
        VBox labelledEventEndPicker = new VBox(eventEndLabel, eventEndPicker);
        HBox eventDatePicker = new HBox(5, labelledEventStartPicker, labelledEventEndPicker);

        Label addButtonLabel = new Label(" ");
        Button addButton = new Button("Add");
        VBox labelledAddButton = new VBox(addButtonLabel, addButton);
        Button deleteButton = new Button("Delete");
        Button markDoneButton = new Button("Mark Done");
        Button markUndoneButton = new Button("Mark Undone");
        Button byeButton = new Button("Bye");

        // Layout
        VBox inputFields = new VBox(10, labelledTaskInput);
        HBox inputBox = new HBox(10, labelledTaskType, inputFields, taskPicker.getNode(), labelledAddButton);
        HBox buttonBox = new HBox(10, markDoneButton, markUndoneButton, deleteButton, byeButton);
        VBox layout = new VBox(10, taskList.getTasksView(), inputBox, buttonBox);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Event Handlers
        taskType.setOnAction(e -> {
            inputFields.getChildren().clear();
            inputFields.getChildren().add(labelledTaskInput);
            if (taskType.getValue().equals("Deadline")) {
                inputFields.getChildren().add(labelledDeadlinePicker);
            } else if (taskType.getValue().equals("Event")) {
                inputFields.getChildren().add(eventDatePicker);
            }
        });

        addButton.setOnAction(e -> {
            String taskDescription = taskInput.getText().trim();
            if (!taskDescription.isEmpty()) {
                Optional<TaskBuilder<?>> taskBuilder = Optional.empty();
                if (taskType.getValue().equals("Todo")) {
                    TodoBuilder builder = new TodoBuilder(taskDescription);
                    taskBuilder = Optional.of(builder);
                } else if (taskType.getValue().equals("Deadline")) {
                    LocalDate deadline = deadlinePicker.getValue();
                    if (deadline == null) {
                        return;
                    }

                    DeadlineBuilder builder =
                            new DeadlineBuilder(taskDescription, deadline.atStartOfDay());
                    taskBuilder = Optional.of(builder);
                } else if (taskType.getValue().equals("Event")) {
                    LocalDate start = eventStartPicker.getValue();
                    LocalDate end = eventEndPicker.getValue();
                    if (start == null || end == null) {
                        return;
                    }

                    EventBuilder builder = new EventBuilder(taskDescription, start.atStartOfDay(),
                            end.atStartOfDay());
                    taskBuilder = Optional.of(builder);
                }

                if (taskBuilder.isPresent()) {
                    Optional<Task> previousTask = taskPicker.getTaskPicker().getValue();
                    Task task = taskBuilder.get().withPreviousTask(previousTask).build();
                    AddCommand addCommand = new AddCommand(task);

                    addCommand.execute(taskList, storage);
                    taskPicker.setTasks(taskList.getTasks());
                    taskInput.clear();
                    deadlinePicker.setValue(null);
                    eventStartPicker.setValue(null);
                    eventEndPicker.setValue(null);
                }
            }
        });

        deleteButton.setOnAction(e -> {
            int selectedIndex = taskList.getTasksView().getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Task task = taskList.getTaskInView(selectedIndex);
                DeleteCommand deleteCommand = new DeleteCommand(task);
                deleteCommand.execute(taskList, storage);
                taskPicker.setTasks(taskList.getTasks());
            }
        });

        markDoneButton.setOnAction(e -> {
            int selectedIndex = taskList.getTasksView().getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Task task = taskList.getTaskInView(selectedIndex);
                MarkCommand markCommand = new MarkCommand(task);
                markCommand.execute(taskList, storage);
            }
        });

        markUndoneButton.setOnAction(e -> {
            int selectedIndex = taskList.getTasksView().getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Task task = taskList.getTaskInView(selectedIndex);
                UnmarkCommand unmarkCommand = new UnmarkCommand(task);
                unmarkCommand.execute(taskList, storage);
            }
        });

        byeButton.setOnAction(e -> {
            ExitCommand exitCommand = new ExitCommand();
            exitCommand.execute(taskList, storage);
            primaryStage.close();
        });

        // Scene Setup
        Scene scene = new Scene(layout, 750, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Todo List");
        primaryStage.show();
    }
}
