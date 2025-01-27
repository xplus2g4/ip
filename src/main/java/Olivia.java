import java.nio.file.Path;
import java.util.ArrayList;

import olivia.OliviaException;
import olivia.Parser;
import olivia.Storage;
import olivia.Ui;
import olivia.commands.Command;
import olivia.tasks.*;

public class Olivia {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

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

    public static void main(String[] args) {
        Path path = Path.of("data", "olivia.csv");
        Olivia olivia = new Olivia(path);
        olivia.run();
    }
}
