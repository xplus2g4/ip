package olivia;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import olivia.tasks.Task;
import olivia.tasks.TaskList;

/**
 * Represents the user interface of Olivia.
 */
public class Ui {
    private Scanner scanner;
    private PrintStream out;

    /**
     * Constructs a user interface.
     *
     * @param in The input stream to read user input.
     * @param out The output stream to print messages.
     */
    public Ui(InputStream in, PrintStream out) {
        this.scanner = new Scanner(in);
        this.out = out;
    }

    /**
     * Closes the user interface.
     */
    public void close() {
        this.scanner.close();
    }

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        String logo = " ________  ___       ___  ___      ___ ___  ________     \n" + //
                        "|\\   __  \\|\\  \\     |\\  \\|\\  \\    /  /|\\  \\|\\   __  \\    \n" + //
                        "\\ \\  \\|\\  \\ \\  \\    \\ \\  \\ \\  \\  /  / | \\  \\ \\  \\|\\  \\   \n" + //
                        " \\ \\  \\\\\\  \\ \\  \\    \\ \\  \\ \\  \\/  / / \\ \\  \\ \\   __  \\  \n" + //
                        "  \\ \\  \\\\\\  \\ \\  \\____\\ \\  \\ \\    / /   \\ \\  \\ \\  \\ \\  \\ \n" + //
                        "   \\ \\_______\\ \\_______\\ \\__\\ \\__/ /     \\ \\__\\ \\__\\ \\__\\\n" + //
                        "    \\|_______|\\|_______|\\|__|\\|__|/       \\|__|\\|__|\\|__|\n" + //
                        "                                                         \n" + //
                        "                                                         ";
        this.out.println(logo);
        this.out.println("  Hello! I'm Olivia\n  What can I do for you?");
        this.showLine();
    }

    /**
     * Reads the raw command from the user.
     *
     * @return The raw command.
     */
    public String readRawCommand() {
        return this.scanner.nextLine();
    }

    /**
     * Shows the task list.
     *
     * @param tasks The task list to show.
     */
    public void showTaskList(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("  Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append("  " + (i + 1) + ". " + tasks.getTask(i) + "\n");
        }

        this.out.print(sb.toString());
    }

    /**
     * Shows the task list with a message.
     *
     * @param task The task item to show as marked.
     */
    public void showTaskMarked(Task item) {
        StringBuilder sb = new StringBuilder();
        sb.append("  Nice! I've marked this task as done:\n");
        sb.append("    " + item.toString());

        this.out.println(sb.toString());
    }

    /**
     * Shows the task list with a message.
     * @param task The task item to show as unmarked.
     */
    public void showTaskUnmarked(Task item) {
        StringBuilder sb = new StringBuilder();
        sb.append("  OK, I've marked this task as not done yet:\n");
        sb.append("    " + item.toString());

        this.out.println(sb.toString());
    }

    /**
     * Shows the task list with a message.
     *
     * @param item The task item to show as added.
     * @param size The size of the task list after adding.
     */
    public void showTaskAdded(Task item, int size) {
        StringBuilder sb = new StringBuilder();
        sb.append("  Got it. I've added this task:\n");
        sb.append("    " + item.toString() + "\n");
        sb.append("  Now you have " + size + " tasks in the list.");

        this.out.println(sb.toString());
    }

    /**
     * Shows the task list with a message.
     *
     * @param item The task item to show as deleted.
     * @param size The size of the task list after deleting.
     */
    public void showTaskDeleted(Task item, int size) {
        StringBuilder sb = new StringBuilder();
        sb.append("  Noted. I've removed this task:\n");
        sb.append("    " + item.toString() + "\n");
        sb.append("  Now you have " + size + " tasks in the list.");

        this.out.println(sb.toString());
    }

    /**
     * Shows the goodbye message.
     */
    public void showGoodbye() {
        this.out.println("  Bye. Hope to see you again soon!");
    }

    /**
     * Shows an error message.
     *
     * @param e The Olivia exception.
     */
    public void showError(OliviaException e) {
        this.out.println("  " + e.getMessage());
    }

    /**
     * Shows a line.
     */
    public void showLine() {
        this.out.println("  ------------------------------------");
    }
}
