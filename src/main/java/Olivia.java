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
        List<String> items = new ArrayList<>();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                printConsoleMessage(listItems(items));
            } else {
                items.add(input);
                printConsoleMessage("added: " + input);
            }
            input = sc.nextLine();
        }
        sc.close();
        printConsoleMessage("Bye. Hope to see you again soon!");
    }

    private static void printConsoleMessage(String message) {
        System.out.println("  ------------------------------------");
        System.out.println("  " + message);
        System.out.println("  ------------------------------------");
    }

    public static String listItems(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            sb.append("  " + (i + 1) + ". " + items.get(i) + "\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
