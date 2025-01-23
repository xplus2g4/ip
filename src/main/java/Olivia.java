import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Olivia {
    public static void main(String[] args) {
        String logo = " ________  ___       ___  ___      ___ ___  ________     \n" + //
                        "|\\   __  \\|\\  \\     |\\  \\|\\  \\    /  /|\\  \\|\\   __  \\    \n" + //
                        "\\ \\  \\|\\  \\ \\  \\    \\ \\  \\ \\  \\  /  / | \\  \\ \\  \\|\\  \\   \n" + //
                        " \\ \\  \\\\\\  \\ \\  \\    \\ \\  \\ \\  \\/  / / \\ \\  \\ \\   __  \\  \n" + //
                        "  \\ \\  \\\\\\  \\ \\  \\____\\ \\  \\ \\    / /   \\ \\  \\ \\  \\ \\  \\ \n" + //
                        "   \\ \\_______\\ \\_______\\ \\__\\ \\__/ /     \\ \\__\\ \\__\\ \\__\\\n" + //
                        "    \\|_______|\\|_______|\\|__|\\|__|/       \\|__|\\|__|\\|__|\n" + //
                        "                                                         \n" + //
                        "                                                         ";
        System.out.println(logo);
        System.out.println("  Hello! I'm Olivia\n  What can I do for you?");
        System.out.println("  ------------------------------------");

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        List<Task> items = new ArrayList<>();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                printConsoleMessage(getItemsString(items));
            } else if (input.startsWith("mark")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                items.get(index).markAsDone();
                printConsoleMessage(getMarkedString(items.get(index)));
            } else if (input.startsWith("unmark")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                items.get(index).markAsUndone();
                printConsoleMessage(getMarkedString(items.get(index)));
            } else {
                items.add(new Task(input));
                printConsoleMessage("  added: " + input);
            }
            input = sc.nextLine();
        }
        sc.close();
        printConsoleMessage("Bye. Hope to see you again soon!");
    }

    private static void printConsoleMessage(String message) {
        System.out.println("  ------------------------------------");
        System.out.println(message);
        System.out.println("  ------------------------------------");
    }

    public static String getItemsString(List<Task> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("  Here are the tasks in your list:\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append("  " + (i + 1) + ". " + items.get(i) + "\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String getMarkedString(Task item) {
        StringBuilder sb = new StringBuilder();
        if (item.isDone()) {
            sb.append("  Nice! I've marked this task as done:\n");
        } else {
            sb.append("  OK, I've marked this task as not done yet:\n");
        }
        sb.append("    " + item.toString());
        return sb.toString();
    }
}
