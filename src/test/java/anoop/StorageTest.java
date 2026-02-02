package anoop;

import anoop.exception.LoadFailedException;
import anoop.exception.SaveFailedException;
import anoop.task.Deadline;
import anoop.task.Task;
import anoop.task.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {
    private Path dataDir;
    private Path dataFile;
    private boolean hadDataDir;
    private byte[] originalData;

    @BeforeEach
    public void setUp() throws IOException {
        dataDir = Paths.get("data");
        dataFile = dataDir.resolve("data.txt");
        hadDataDir = Files.exists(dataDir);

        if (Files.exists(dataFile)) {
            originalData = Files.readAllBytes(dataFile);
        }

        Files.createDirectories(dataDir);
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (originalData != null) {
            Files.write(dataFile, originalData);
        } else {
            Files.deleteIfExists(dataFile);
        }

        if (!hadDataDir) {
            Files.deleteIfExists(dataDir);
        }
    }

    @Test
    public void loadTasks_missingFile_returnsEmptyList() throws LoadFailedException, IOException {
        Files.deleteIfExists(dataFile);
        Storage storage = new Storage();
        List<Task> tasks = storage.loadTasks();
        assertEquals(0, tasks.size());
    }

    @Test
    public void saveTasks_thenLoad_roundTripsTasks() throws SaveFailedException, LoadFailedException {
        Storage storage = new Storage();
        List<Task> input = new ArrayList<>();
        input.add(new Todo("read book", false));
        input.add(new Deadline("submit", true, LocalDateTime.of(2026, 1, 29, 20, 0)));

        storage.saveTasks(input);
        List<Task> loaded = storage.loadTasks();

        assertEquals(2, loaded.size());
        assertEquals(input.get(0).toString(), loaded.get(0).toString());
        assertEquals(input.get(1).toString(), loaded.get(1).toString());
    }

    @Test
    public void loadTasks_skipsCorruptedLines() throws IOException, LoadFailedException {
        Storage storage = new Storage();
        Deadline deadline = new Deadline("submit", false, LocalDateTime.of(2026, 1, 29, 20, 0));

        List<String> lines = List.of(
                new Todo("read book", false).toString(),
                "bad line",
                deadline.toString()
        );
        Files.write(dataFile, lines);

        List<Task> loaded = storage.loadTasks();

        assertEquals(2, loaded.size());
        assertEquals(lines.get(0), loaded.get(0).toString());
        assertEquals(lines.get(2), loaded.get(1).toString());
        assertTrue(Files.exists(dataFile));
    }
}
