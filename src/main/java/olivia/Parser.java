package olivia;

import olivia.commands.*;

/**
 * Represents a parser to parse user input.
 */
public class Parser {
    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param input The user input.
     * @return The corresponding command.
     * @throws OliviaException If the user input is invalid.
     */
    public static Command parse(String input) throws OliviaException {
        if (input.equals("bye")) {
            return new ExitCommand(input);
        } else if (input.equals("list")) {
            return new ListCommand(input);
        } else if (input.startsWith("mark")) {
            return new MarkCommand(input);
        } else if (input.startsWith("unmark")) {
            return new UnmarkCommand(input);
        } else if (input.startsWith("delete")) {
            return new DeleteCommand(input);
        } else if (input.startsWith("find")) {
            return new FindCommand(input);
        } else if (input.startsWith("todo") || input.startsWith("deadline")
                || input.startsWith("event")) {
            return new AddCommand(input);
        }
        throw new OliviaException("I'm sorry, but I don't know what that means :-(");
    }
}
