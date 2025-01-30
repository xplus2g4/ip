package olivia;

import org.junit.jupiter.api.Test;

import olivia.commands.ExitCommand;

/**
 * Represents a command test.
 */
public class CommandTest {
    /**
     * Tests the exit command.
     */
    @Test
    public void exitCommandTest() {
        ExitCommand exitCommand = new ExitCommand("bye");
        assert exitCommand.isExit();
    }
}
