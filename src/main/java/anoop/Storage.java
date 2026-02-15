package anoop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import anoop.exception.InvalidTaskFormatException;
import anoop.exception.LoadFailedException;
import anoop.exception.SaveFailedException;
import anoop.task.Task;
import anoop.task.TaskFactory;



/**
 * Represents a task storage to save/load tasks in the disk.
 */
public class Storage {
    /** Relative file directory for the data file. */
    private static final String DIR = "data";
    /** Data file name used to store tasks. */
    private static final String FILE = "data.txt";
    /** Directory object representing the data folder. */
    private final File dir;
    /** File object representing the task data file. */
    private final File file;

    /**
     * Constructor for the {@link Storage} class.
     */
    public Storage() {
        this.dir = new File(DIR);
        this.file = new File(DIR, FILE);
        assert this.dir != null : "data directory must be initialized";
        assert this.file != null : "data file must be initialized";
    }

    /**
     * Helper method to ensure the save file and its directory exists.
     *
     * @throws IOException creation of file or directory fails.
     */
    private void ensureFileExists() throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create data directory.");
        assert dir != null : "data directory must not be null";
        assert file != null : "data file must not be null";
        if (!dir.exists()) {
            dir.mkdir();
            assert dir.exists() : "data directory should exist after mkdir";
        }
        if (!file.exists()) {
            file.createNewFile();
            assert file.exists() : "data file should exist after createNewFile";
        }
    }

    /**
     * Saves input task list to the disk.
     * @param tasks the list of tasks.
     * @throws SaveFailedException when I/O error occurs when writing to the file.
     */
    public void saveTasks(List<Task> tasks) throws SaveFailedException {
        assert tasks != null : "tasks must not be null";
        try {
            this.ensureFileExists();
            FileWriter fw = new FileWriter(this.file);
            for (Task t : tasks) {
                assert t != null : "task entries must not be null";
                fw.write(t.toString());
                fw.write(System.lineSeparator());
            try (FileWriter fw = new FileWriter(this.file)) {
                for (Task t : tasks) {
                    fw.write(t.toString());
                    fw.write(System.lineSeparator());
                }
            }
            fw.close();
        } catch (IOException e) {
            throw new SaveFailedException();
        }
    }

    /**
     * Loads a list of tasks from the disk.
     * @return a list of tasks loaded from the disk.
     * @throws LoadFailedException when I/O error occurs when reading from the file.
     */
    public List<Task> loadTasks() throws LoadFailedException {
        try {
            this.ensureFileExists();
            List<Task> tasks = new ArrayList<>();
            assert tasks != null : "loaded tasks list must be initialized";
            if (!this.file.exists()) {
                return tasks;
            }

            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    try {
                        String line = sc.nextLine();
                        Task t = this.parse(line);
                        tasks.add(t);
                    } catch (InvalidTaskFormatException e) {
                        System.out.println("Skipping corrupted line.");
                    }
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new LoadFailedException();
        }
    }

    /**
     * Helper method to parse the string representation of a task.
     * @param line string representation of a task.
     * @return a Task object based on the input string.
     * @throws InvalidTaskFormatException if input string does not follow the required format.
     */
    private Task parse(String line) throws InvalidTaskFormatException {
        assert line != null : "line must not be null";
        if (line.length() < 6 || line.charAt(0) != '[' || line.charAt(2) != ']'
                || line.charAt(3) != '[' || line.charAt(5) != ']') {
            throw new InvalidTaskFormatException("Invalid task format: " + line);
        }

        char taskType = line.charAt(1);
        char taskCompletion = line.charAt(4);
        boolean isDone = taskCompletion == 'X';
        String fields = line.substring(6).trim();

        switch (taskType) {
        case 'T':
            return TaskFactory.createTaskFromData(taskType, isDone, fields);
        case 'D':
            return parseDeadlineFromFields(line, isDone, fields);
        case 'E':
            return parseEventFromFields(line, isDone, fields);
        default:
            throw new InvalidTaskFormatException("Unknown task type: " + taskType);

        }
    }

    private Task parseDeadlineFromFields(String line, boolean isDone, String fields) throws InvalidTaskFormatException {
        int byIndex = fields.indexOf("(by:");
        if (byIndex == -1 || !fields.endsWith(")")) {
            throw new InvalidTaskFormatException("Invalid task format: " + line);
        }

        String desc = fields.substring(0, byIndex).trim();
        String by = fields.substring(byIndex + 5, fields.length() - 1).trim();
        return TaskFactory.createTaskFromData('D', isDone, desc, by);
    }

    private Task parseEventFromFields(String line, boolean isDone, String fields) throws InvalidTaskFormatException {
        int fromIndex = fields.indexOf("(from:");
        int toIndex = fields.indexOf(" to:");
        if (fromIndex == -1 || toIndex == -1 || toIndex <= fromIndex || !fields.endsWith(")")) {
            throw new InvalidTaskFormatException("Invalid task format: " + line);
        }

        String desc = fields.substring(0, fromIndex).trim();
        String start = fields.substring(fromIndex + 6, toIndex).trim();
        String end = fields.substring(toIndex + 4, fields.length() - 1).trim();
        return TaskFactory.createTaskFromData('E', isDone, desc, start, end);
    }

}
