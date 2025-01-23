public enum Command {
    BYE,
    LIST,
    MARK,
    UNMARK,
    DELETE,
    ADD;

    public static Command fromString(String input) throws OliviaException {
        if (input.equals("bye")) {
            return BYE;
        } else if (input.equals("list")) {
            return LIST;
        } else if (input.startsWith("mark")) {
            return MARK;
        } else if (input.startsWith("unmark")) {
            return UNMARK;
        } else if (input.startsWith("delete")) {
            return DELETE;
        } else if (input.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event")) {
            return ADD;
        }
        throw new OliviaException("I'm sorry, but I don't know what that means :-(");
    }
}
