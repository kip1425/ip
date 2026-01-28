package anoop;

import anoop.exception.InvalidTaskFormatException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a task storage to save/load tasks in the disk.
 */

public class Storage {
    /** Relative file directory for the data file. */
    private static final String DIR = "data";

    /** Directory object representing the data folder. */
    private final File dir;

    /** Data file name used to store tasks. */
    private static final String FILE = "data.txt";

    /** File object representing the task data file. */
    private final File file;

    /**
     * Instantiates a storage object to save/load tasks.
     * @throws IOException if directory/file cannot be created due to I/O errors.
     */
    public Storage() throws IOException {
        this.dir = new File(DIR);
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }

        this.file = new File(DIR, FILE);
        if (!this.file.exists()) {
            this.file.createNewFile();
        }
    }

    /**
     * Saves input task list to the disk.
     * @param tasks the list of tasks.
     * @throws IOException when I/O error occurs when writing to the file.
     */
    public void saveTasks(List<Task> tasks) throws IOException {
        FileWriter fw = new FileWriter(this.file);
        for (Task t : tasks) {
            fw.write(t.toString());
            fw.write(System.lineSeparator());
        }

        fw.close();
    }

    /**
     * Loads a list of tasks from the disk.
     * @return a list of tasks loaded from the disk.
     * @throws IOException when I/O error occurs when reading from the file.
     */
    public List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        if (!this.file.exists()) {
            return tasks;
        }

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            try {
                String line = sc.nextLine();
                Task t = this.parse(line);
                tasks.add(t);
            } catch (InvalidTaskFormatException e) {
                System.out.println("Skipping corrupted line.");
            }
        }
        return tasks;
    }

    /**
     * Helper method to parse the string representation of a task.
     * @param line string representation of a task.
     * @return a Task object based on the input string.
     * @throws InvalidTaskFormatException if input string does not follow the required format.
     */
    private Task parse(String line) throws InvalidTaskFormatException {
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
            case 'D': {
                int byIndex = fields.indexOf("(by:");

                if (byIndex == -1 || !fields.endsWith(")")) {
                    throw new InvalidTaskFormatException("Invalid task format: " + line);
                }

                String desc = fields.substring(0, byIndex).trim();
                String by = fields.substring(byIndex + 5, fields.length() - 1).trim();

                return TaskFactory.createTaskFromData('D', isDone, desc, by);
            }
            case 'E': {
                int fromIndex = fields.indexOf("(from:");
                int toIndex = fields.indexOf("to:");

                if (fromIndex == -1 || toIndex == -1 || !fields.endsWith(")")) {
                    throw new InvalidTaskFormatException("Invalid task format: " + line);
                }

                String desc = fields.substring(0, fromIndex).trim();
                String start = fields.substring(fromIndex + 6, toIndex).trim();
                String end = fields.substring(toIndex + 3, fields.length() - 1).trim();

                return TaskFactory.createTaskFromData('E', isDone, desc, start, end);
            }
            default:
                throw new InvalidTaskFormatException("Unknown task type: " + taskType);

        }
    }

}
