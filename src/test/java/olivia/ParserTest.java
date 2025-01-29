package olivia;

import org.junit.jupiter.api.Test;

import olivia.commands.*;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class ParserTest {
    @Test
    public void parseCommandTest() throws OliviaException{
        assertInstanceOf(ExitCommand.class, Parser.parse("bye"));
        assertInstanceOf(ListCommand.class, Parser.parse("list"));
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1"));
        assertInstanceOf(UnmarkCommand.class, Parser.parse("unmark 1"));
        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 1"));
        assertInstanceOf(AddCommand.class, Parser.parse("todo read book"));
        assertInstanceOf(AddCommand.class, Parser.parse("deadline return book /by 29/01/2025 00:00"));
        assertInstanceOf(AddCommand.class, Parser.parse("event project meeting /at 29/01/2025 00:00"));
    }

    @Test
    public void parseUnknownCommandTest(){
        assertThrowsExactly(OliviaException.class, () -> Parser.parse("unknown"));
    }
}
