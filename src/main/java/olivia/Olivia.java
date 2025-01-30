package olivia;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import olivia.commands.AddCommand;
import olivia.commands.DeleteCommand;
import olivia.commands.ExitCommand;
import olivia.commands.MarkCommand;
import olivia.commands.UnmarkCommand;
import olivia.tasks.Deadline;
import olivia.tasks.Event;
import olivia.tasks.Task;
import olivia.tasks.TaskList;
import olivia.tasks.Todo;

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
        this.storage = new Storage(PATH);

        try {
            this.taskList = new TaskList(storage.readTasks());
        } catch (OliviaException e) {
            taskList = new TaskList(new ArrayList<>());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        ComboBox<String> taskType = new ComboBox<>();
        taskType.getItems().addAll("Todo", "Deadline", "Event");
        taskType.setValue("Todo");

        TextField taskInput = new TextField();
        DatePicker deadlinePicker = new DatePicker();
        DatePicker eventStartPicker = new DatePicker();
        DatePicker eventEndPicker = new DatePicker();

        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button markDoneButton = new Button("Mark Done");
        Button markUndoneButton = new Button("Mark Undone");
        Button byeButton = new Button("Bye");

        // Layout
        VBox inputFields = new VBox(10, taskInput);
        HBox inputBox = new HBox(10, taskType, inputFields, addButton);
        HBox buttonBox = new HBox(10, markDoneButton, markUndoneButton, deleteButton, byeButton);
        VBox layout = new VBox(10, taskList.getTasksView(), inputBox, buttonBox);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Event Handlers
        taskType.setOnAction(e -> {
            inputFields.getChildren().clear();
            inputFields.getChildren().add(taskInput);
            if (taskType.getValue().equals("Deadline")) {
                inputFields.getChildren().add(deadlinePicker);
            } else if (taskType.getValue().equals("Event")) {
                inputFields.getChildren().addAll(eventStartPicker, eventEndPicker);
            }
        });

        addButton.setOnAction(e -> {
            String taskDescription = taskInput.getText().trim();
            if (!taskDescription.isEmpty()) {
                AddCommand addCommand = new AddCommand(new Todo(taskDescription));
                if (taskType.getValue().equals("Deadline")) {
                    LocalDate deadline = deadlinePicker.getValue();
                    if (deadline == null) {
                        return;
                    }
                    addCommand =
                            new AddCommand(new Deadline(taskDescription, deadline.atStartOfDay()));
                } else if (taskType.getValue().equals("Event")) {
                    LocalDate start = eventStartPicker.getValue();
                    LocalDate end = eventEndPicker.getValue();
                    if (start == null || end == null) {
                        return;
                    }
                    addCommand = new AddCommand(
                            new Event(taskDescription, start.atStartOfDay(), end.atStartOfDay()));
                } else if (taskType.getValue().equals("Event")) {
                    inputFields.getChildren().addAll(eventStartPicker, eventEndPicker);
                }
                addCommand.execute(taskList, storage);
                taskInput.clear();
                deadlinePicker.setValue(null);
                eventStartPicker.setValue(null);
                eventEndPicker.setValue(null);
            }
        });

        deleteButton.setOnAction(e -> {
            int selectedIndex = taskList.getTasksView().getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Task task = taskList.getTaskInView(selectedIndex);
                DeleteCommand deleteCommand = new DeleteCommand(task);
                deleteCommand.execute(taskList, storage);
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
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Todo List");
        primaryStage.show();
    }
}
