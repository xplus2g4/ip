import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import tasks.*;

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
        List<Task> items = new ArrayList<>();
        String input;

        loop: do {
            try {
                input = sc.nextLine();
                Command command = Command.fromString(input);
                switch (command) {
                    case BYE:
                        break loop;
                    case LIST:
                        commandListItems(items);
                        break;
                    case MARK:
                        commandMarkItem(items, input);
                        break;
                    case UNMARK:
                        commandUnmarkItem(items, input);
                        break;
                    case DELETE:
                        commandDeleteItem(items, input);
                        break;
                    case ADD:
                        commandAddItem(items, input);
                        break;
                }
            } catch (OliviaException e) {
                printConsoleMessage("  " + e.getMessage());
            }
        } while (true);
        sc.close();
        printConsoleMessage("  Bye. Hope to see you again soon!");
    }

    private static void printConsoleMessage(String message) {
        System.out.println("  ------------------------------------");
        System.out.println(message);
        System.out.println("  ------------------------------------");
    }

    public static void commandListItems(List<Task> items) {
        printConsoleMessage(getItemsString(items));
    }

    public static void commandMarkItem(List<Task> items, String input) {
        int index = Integer.parseInt(input.substring(5)) - 1;
        items.get(index).markAsDone();
        printConsoleMessage(getMarkedString(items.get(index)));
    }

    public static void commandUnmarkItem(List<Task> items, String input) {
        int index = Integer.parseInt(input.substring(7)) - 1;
        items.get(index).markAsUndone();
        printConsoleMessage(getMarkedString(items.get(index)));
    }

    public static void commandDeleteItem(List<Task> items, String input) {
        int index = Integer.parseInt(input.substring(7)) - 1;
        Task item = items.remove(index);
        StringBuilder sb = new StringBuilder();
        sb.append("  Noted. I've removed this task:\n");
        sb.append("    " + item.toString() + "\n");
        sb.append("  Now you have " + items.size() + " tasks in the list.");
        printConsoleMessage(sb.toString());
    }

    public static void commandAddItem(List<Task> items, String input) throws OliviaException {
        StringBuilder sb = new StringBuilder();
        try {
            Optional<? extends Task> task = Todo.isTodo(input);
            if (task.isPresent()) {
                items.add(task.get());
            } else if ((task = Deadline.isDeadline(input)).isPresent()) {
                items.add(task.get());
            } else if ((task = Event.isEvent(input)).isPresent()) {
                items.add(task.get());
            } else {
                throw new IllegalArgumentException("I'm sorry, but I don't know what that means :-(");
            }
            sb.append("  Got it. I've added this task:\n");
            sb.append("    " + task.get().toString() + "\n");
            sb.append("  Now you have " + items.size() + " tasks in the list.");
            printConsoleMessage(sb.toString());
        } catch (IllegalArgumentException e) {
            throw new OliviaException(e.getMessage());
        }
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
