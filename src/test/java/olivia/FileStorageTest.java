package olivia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import olivia.storage.FileStorage;
import olivia.tasks.Deadline;
import olivia.tasks.DeadlineBuilder;
import olivia.tasks.Event;
import olivia.tasks.EventBuilder;
import olivia.tasks.Task;
import olivia.tasks.Todo;
import olivia.tasks.TodoBuilder;

/**
 * Represents a file storage test.
 */
public class FileStorageTest {
    @Test()
    public void taskStringWithInvalidTypeTest() {
        String taskString = "17b33aa5-087a-43de-8ff4-1140077e13f8|invalid-type|0|sadsad|null";

        assertThrows(OliviaException.class, () ->
            FileStorage.fromString(new HashMap<>(), taskString));
    }

    @Test()
    public void taskStringWithInvalidNumOfArgsTest() {
        String taskString = "17b33aa5-087a-43de-8ff4-1140077e13f8|invalid-type|0|sadsad";

        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            FileStorage.fromString(new HashMap<>(), taskString));
    }

    @Test()
    public void taskStringWithInvalidUuidTest() {
        String taskString = "invalid-uuid|T|0|sadsad|null";

        assertThrows(IllegalArgumentException.class, () ->
            FileStorage.fromString(new HashMap<>(), taskString));
    }

    @Test()
    public void validTodoStringTest() {
        String todoString = "17b33aa5-087a-43de-8ff4-1140077e13f8|T|0|sadsad|null";
        Task actualTask = FileStorage.fromString(new HashMap<>(), todoString);
        Todo expectedTodo = new TodoBuilder()
                .withId(UUID.fromString("17b33aa5-087a-43de-8ff4-1140077e13f8"))
                .withDescription("sadsad")
                .withIsDone(false)
                .build();

        assertNotNull(actualTask);
        assertTrue(actualTask instanceof Todo);
        Todo actualTodo = (Todo) actualTask;

        assertEquals(expectedTodo, actualTodo);
        assertEquals(expectedTodo.getType(), actualTodo.getType());
        assertEquals(expectedTodo.getDescription(), actualTodo.getDescription());
        assertEquals(expectedTodo.isDone(), actualTodo.isDone());
        assertEquals(expectedTodo.getPreviousTask(), actualTodo.getPreviousTask());
    }

    @Test()
    public void validDeadlineStringTest() {
        String taskString = "29f49a82-7d90-46e9-951b-f0d67251ed9f|D|1|a deadline|null|13/02/2025 00:00";
        Task actualTask = FileStorage.fromString(new HashMap<>(), taskString);

        LocalDateTime deadlineTime = LocalDateTime.parse("13/02/2025 00:00", Task.WRITE_FORMATTER);
        Deadline expectedDeadline = new DeadlineBuilder()
                .withId(UUID.fromString("29f49a82-7d90-46e9-951b-f0d67251ed9f"))
                .withDescription("a deadline")
                .withIsDone(true)
                .withDeadline(deadlineTime)
                .build();

        assertNotNull(actualTask);
        assertTrue(actualTask instanceof Deadline);
        Deadline actualDeadline = (Deadline) actualTask;

        assertEquals(expectedDeadline, actualTask);
        assertEquals(expectedDeadline.getType(), actualTask.getType());
        assertEquals(expectedDeadline.getDescription(), actualTask.getDescription());
        assertEquals(expectedDeadline.isDone(), actualTask.isDone());
        assertEquals(expectedDeadline.getPreviousTask(), actualTask.getPreviousTask());
        assertEquals(expectedDeadline.getDeadline(), actualDeadline.getDeadline());
    }

    @Test()
    public void deadlineStringWithInvalidNumOfArgsTest() {
        String deadlineString = "29f49a82-7d90-46e9-951b-f0d67251ed9f|D|1|a deadline|null";

        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            FileStorage.fromString(new HashMap<>(), deadlineString));
    }

    @Test()
    public void deadlineStringWithInvalidDeadlineTest() {
        String deadlineString = "29f49a82-7d90-46e9-951b-f0d67251ed9f|D|1|a deadline|null|13/02/2025";

        assertThrows(DateTimeParseException.class, () ->
            FileStorage.fromString(new HashMap<>(), deadlineString));
    }

    @Test()
    public void validEventStringTest() {
        String todoString = "17b33aa5-087a-43de-8ff4-1140077e13f8|E|0|an event|null|20/02/2025 00:00|22/02/2025 00:00";
        Task actualTask = FileStorage.fromString(new HashMap<>(), todoString);

        LocalDateTime startTime = LocalDateTime.parse("20/02/2025 00:00", Task.WRITE_FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse("22/02/2025 00:00", Task.WRITE_FORMATTER);
        Event expectedEvent = new EventBuilder()
                .withId(UUID.fromString("17b33aa5-087a-43de-8ff4-1140077e13f8"))
                .withDescription("an event")
                .withIsDone(false)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .build();

        assertNotNull(actualTask);
        assertTrue(actualTask instanceof Event);
        Event actualEvent = (Event) actualTask;

        assertEquals(expectedEvent, actualEvent);
        assertEquals(expectedEvent.getType(), actualEvent.getType());
        assertEquals(expectedEvent.getDescription(), actualEvent.getDescription());
        assertEquals(expectedEvent.isDone(), actualEvent.isDone());
        assertEquals(expectedEvent.getPreviousTask(), actualEvent.getPreviousTask());
        assertEquals(expectedEvent.getDuration(), actualEvent.getDuration());
    }

    @Test()
    public void eventStringWithInvalidNumOfArgsTest() {
        String eventString = "17b33aa5-087a-43de-8ff4-1140077e13f8|E|0|an event|null|20/02/2025 00:00";

        assertThrows(ArrayIndexOutOfBoundsException.class, () ->
            FileStorage.fromString(new HashMap<>(), eventString));
    }

    @Test()
    public void eventStringWithInvalidStartTimeTest() {
        String eventString = "17b33aa5-087a-43de-8ff4-1140077e13f8|E|0|an event|null|20/02/2025|22/02/2025";

        assertThrows(DateTimeParseException.class, () ->
            FileStorage.fromString(new HashMap<>(), eventString));
    }
}
