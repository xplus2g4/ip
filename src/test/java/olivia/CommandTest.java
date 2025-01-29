package olivia;

import org.junit.jupiter.api.Test;

import olivia.commands.*;

public class CommandTest {
    @Test
    public void exitCommandTest() {
        ExitCommand exitCommand = new ExitCommand("bye");
        assert exitCommand.isExit();
    }
}
