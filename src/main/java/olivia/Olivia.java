package olivia;
import java.nio.file.Path;
import java.util.ArrayList;

import olivia.commands.Command;
import olivia.tasks.TaskList;

/**
 * Represents the main Olivia chatbot class.
 */
public class Olivia {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    /**
     * Constructs an Olivia chatbot.
     *
     * @param path The path to the file to store tasks.
     */
    public Olivia(Path path) {
        this.ui = new Ui(System.in, System.out);
        this.storage = new Storage(path);

        try {
            this.tasks = new TaskList(storage.readTasks());
        } catch (OliviaException e) {
            this.ui.showError(e);
            tasks = new TaskList(new ArrayList<>());
        }
    }

    /**
     * Runs the Olivia chatbot.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String rawCommand = ui.readRawCommand();
                ui.showLine();
                Command command = Parser.parse(rawCommand);
                command.execute(tasks, storage, ui);
                isExit = command.isExit();
            } catch (OliviaException e) {
                ui.showError(e);
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * The main method to run Olivia.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Path path = java.nio.file.Paths.get("data", "olivia.csv");
        Olivia olivia = new Olivia(path);
        olivia.run();
    }
}
