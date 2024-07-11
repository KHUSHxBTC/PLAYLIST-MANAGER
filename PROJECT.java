import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskListApplication {
    private ArrayList<String> tasks;
    private Scanner scanner;
    private final String FILE_NAME = "tasks.txt";

    public TaskListApplication() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadTasksFromFile();
    }

    public void run() {
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    markTaskAsCompleted();
                    break;
                case 3:
                    displayTasks();
                    break;
                case 4:
                    saveTasksToFile();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\nTask List Application");
        System.out.println("1. Add Task");
        System.out.println("2. Mark Task as Completed");
        System.out.println("3. Display Tasks");
        System.out.println("4. Save and Exit");
        System.out.print("Enter your choice: ");
    }

    private void addTask() {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        tasks.add(description);
        System.out.println("Task added successfully!");
    }

    private void markTaskAsCompleted() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        displayTasks();

        System.out.print("Enter task number to mark as completed: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (taskNumber >= 1 && taskNumber <= tasks.size()) {
            // Mark task as completed
            String task = tasks.get(taskNumber - 1);
            if (!task.startsWith("*")) {
                tasks.set(taskNumber - 1, "*" + task);
                System.out.println("Task marked as completed!");
            } else {
                System.out.println("Task is already completed.");
            }
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        System.out.println("\nTasks:");
        for (int i = 0; i < tasks.size(); i++) {
            String task = tasks.get(i);
            System.out.println((i + 1) + ". " + task + " - " +
                    (isTaskCompleted(task) ? "Completed" : "Pending"));
        }
    }

    private boolean isTaskCompleted(String task) {
        return task.startsWith("*");
    }

    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }
    }

    private void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String task : tasks) {
                writer.write(task);
                writer.newLine();
            }
            System.out.println("Tasks saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        TaskListApplication app = new TaskListApplication();
        app.run();
    }
}
