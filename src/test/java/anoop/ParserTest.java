package anoop;

import anoop.command.AddCommand;
import anoop.command.ByeCommand;
import anoop.command.Command;
import anoop.command.DeleteCommand;
import anoop.command.ListCommand;
import anoop.command.MarkCommand;
import anoop.command.UnmarkCommand;
import anoop.exception.AnoopException;
import anoop.task.Deadline;
import anoop.task.Event;
import anoop.task.Task;
import anoop.task.Todo;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    private static <T> T getField(Object target, String name, Class<T> type) {
        try {
            Field field = target.getClass().getDeclaredField(name);
            field.setAccessible(true);

            return type.cast(field.get(target));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError("Failed to access field: " + name, e);
        }
    }

    private static String getTaskDescription(Task task) {
        try {
            Field field = Task.class.getDeclaredField("description");
            field.setAccessible(true);

            return (String) field.get(task);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError("Failed to access task description", e);
        }
    }

    private static boolean getTaskIsDone(Task task) {
        try {
            Field field = Task.class.getDeclaredField("isDone");
            field.setAccessible(true);

            return (boolean) field.get(task);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError("Failed to access task isDone", e);
        }
    }

    @Test
    public void parse_bye_returnsByeCommand() throws Exception {
        Command cmd = Parser.parse("bye");

        assertInstanceOf(ByeCommand.class, cmd);
    }

    @Test
    public void parse_list_returnsListCommand() throws Exception {
        Command cmd = Parser.parse("  list   ");

        assertInstanceOf(ListCommand.class, cmd);
    }

    @Test
    public void parse_mark_parsesIndex() throws Exception {
        Command cmd = Parser.parse("mark 2");

        assertInstanceOf(MarkCommand.class, cmd);
        int index = getField(cmd, "index", Integer.class);
        assertEquals(2, index);
    }

    @Test
    public void parse_unmark_parsesIndex() throws Exception {
        Command cmd = Parser.parse("unmark 5");

        assertInstanceOf(UnmarkCommand.class, cmd);
        int index = getField(cmd, "index", Integer.class);
        assertEquals(5, index);
    }

    @Test
    public void parse_delete_parsesIndex() throws Exception {
        Command cmd = Parser.parse("delete 1");

        assertInstanceOf(DeleteCommand.class, cmd);
        int index = getField(cmd, "index", Integer.class);
        assertEquals(1, index);
    }

    @Test
    public void parse_todo_createsAddCommandWithTodoTask() throws Exception {
        Command cmd = Parser.parse("todo read book");

        assertInstanceOf(AddCommand.class, cmd);
        Task task = getField(cmd, "task", Task.class);
        assertInstanceOf(Todo.class, task);
        assertEquals("read book", getTaskDescription(task));
        assertEquals(false, getTaskIsDone(task));
    }

    @Test
    public void parse_deadline_createsAddCommandWithDeadlineTask() throws Exception {
        Command cmd = Parser.parse("deadline submit /by 2026-01-29 2000");

        assertInstanceOf(AddCommand.class, cmd);
        Task task = getField(cmd, "task", Task.class);
        assertInstanceOf(Deadline.class, task);
        assertEquals("submit", getTaskDescription(task));
        assertEquals(false, getTaskIsDone(task));

        LocalDateTime by = getField(task, "by", LocalDateTime.class);
        assertEquals(LocalDateTime.of(2026, 1, 29, 20, 0), by);
    }

    @Test
    public void parse_event_createsAddCommandWithEventTask() throws Exception {
        Command cmd = Parser.parse("event party /from 2026-01-29 2000 /to 2026-01-29 2300");

        assertInstanceOf(AddCommand.class, cmd);
        Task task = getField(cmd, "task", Task.class);
        assertInstanceOf(Event.class, task);
        assertEquals("party", getTaskDescription(task));
        assertEquals(false, getTaskIsDone(task));

        LocalDateTime start = getField(task, "start", LocalDateTime.class);
        LocalDateTime end = getField(task, "end", LocalDateTime.class);
        assertEquals(LocalDateTime.of(2026, 1, 29, 20, 0), start);
        assertEquals(LocalDateTime.of(2026, 1, 29, 23, 0), end);
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        AnoopException e = assertThrows(AnoopException.class, () -> Parser.parse("unknown"));

        assertEquals("Unknown command entered.", e.getMessage());
    }

    @Test
    public void parse_markMissingIndex_throwsException() {
        AnoopException e = assertThrows(AnoopException.class, () -> Parser.parse("mark"));

        assertEquals("Missing an integer argument to themarkcommand.", e.getMessage());
    }

    @Test
    public void parse_markInvalidIndex_throwsException() {
        AnoopException e = assertThrows(AnoopException.class, () -> Parser.parse("mark one"));

        assertEquals("Task number must be an integer.", e.getMessage());
    }

    @Test
    public void parse_todoMissingDescription_throwsException() {
        AnoopException e = assertThrows(AnoopException.class, () -> Parser.parse("todo   "));

        assertEquals("Todo description cannot be empty.", e.getMessage());
    }

    @Test
    public void parse_deadlineMissingBy_throwsException() {
        AnoopException e = assertThrows(AnoopException.class, () -> Parser.parse("deadline submit"));

        assertEquals("The command format is \"(description) /by (date)\"", e.getMessage());
    }

    @Test
    public void parse_deadlineInvalidDate_throwsException() {
        AnoopException e = assertThrows(AnoopException.class, () -> Parser.parse("deadline submit /by 2026-01-29"));

        assertEquals("Invalid date/time format. Use yyyy-MM-dd HHmm (e.g. 2026-01-29 2000).", e.getMessage());
    }

    @Test
    public void parse_eventMissingParts_throwsException() {
        AnoopException e = assertThrows(AnoopException.class, () -> Parser.parse("event party /from 2026-01-29 2000"));

        assertEquals("The command format is \"(description) /from (date)/to (date)\"", e.getMessage());
    }

    @Test
    public void parse_eventInvalidDate_throwsException() {
        AnoopException e = assertThrows(AnoopException.class,
                () -> Parser.parse("event party /from 2026-01-29 /to 2026-01-29 2300"));

        assertEquals("Invalid date/time format. Use yyyy-MM-dd HHmm (e.g. 2026-01-29 2000).", e.getMessage());
    }
}
