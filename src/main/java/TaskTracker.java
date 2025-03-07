import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskTracker {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: TaskTracker [add|update|delete|mark-in-progress|mark-done|list]");
            System.exit(1);
        }

        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        mapper.setDateFormat(df);

        Task task;

        switch (args[0]) {
            case "add":
                if (args.length != 2) {
                    System.out.println("Usage: TaskTracker add [taskBody]");
                    System.exit(1);
                }

                task = new Task();
                task.setDescription(args[1]);

                mapper.writeValue(new File(task.getId() + ".json"), task);

                System.out.println("Task added successfully (ID: " + task.getId() + ")");
                break;
            case "update":
                if (args.length != 3) {
                    System.out.println("Usage: TaskTracker update [taskID] [newTaskBody]");
                    System.exit(1);
                }

                task = mapper.readValue(new File(args[1] + ".json"), Task.class);
                task.setDescription(args[2]);
                task.setUpdatedAt(new Date());

                mapper.writeValue(new File(args[1] + ".json"), task);
                break;
            case "delete":
                if (args.length != 2) {
                    System.out.println("Usage: TaskTracker delete [taskID]");
                    System.exit(1);
                }

                File taskFile = new File(args[1] + ".json");

                if (!taskFile.delete()) {
                    System.out.println("Failed to delete task: ID not found");
                    System.exit(1);
                }
                break;
            case "mark-in-progress":
                if (args.length != 2) {
                    System.out.println("Usage: TaskTracker mark-in-progress [taskID]");
                    System.exit(1);
                }

                task = mapper.readValue(new File(args[1] + ".json"), Task.class);
                task.setStatus("in-progress");
                task.setUpdatedAt(new Date());

                mapper.writeValue(new File(args[1] + ".json"), task);
                break;
            case "mark-done":
                if (args.length != 2) {
                    System.out.println("Usage: TaskTracker mark-done [taskID]");
                    System.exit(1);
                }

                task = mapper.readValue(new File(args[1] + ".json"), Task.class);
                task.setStatus("done");
                task.setUpdatedAt(new Date());

                mapper.writeValue(new File(args[1] + ".json"), task);
                break;
            case "list":
                if (args.length != 2) {
                    System.out.println("Usage TaskTracker list [done|to-do|in-progress]");
                    System.exit(1);
                }

                ArrayList<Task> taskList = new ArrayList<>();
                File directory = new File(".");
                File[] taskFiles = directory.listFiles();

                if (taskFiles != null) {
                    for (File file : taskFiles) {
                        if (file.getName().matches("(.*).json")) {
                            taskList.add(mapper.readValue(file, Task.class));
                        }
                    }
                }

                for (Task tsk : taskList) {
                    if (args[1].equals("done")) {
                        if (tsk.getStatus().equals("done")) {
                            System.out.println(tsk.getDescription());
                        }
                    }

                    if (args[1].equals("to-do")) {
                        if (tsk.getStatus().equals("to-do")) {
                            System.out.println(tsk.getDescription());
                        }
                    }

                    if (args[1].equals("in-progress")) {
                        if (tsk.getStatus().equals("in-progress")) {
                            System.out.println(tsk.getDescription());
                        }
                    }
                }
                break;

            default:
                System.out.println("Usage: TaskTracker [add|update|delete|mark-in-progress|mark-done|list]");
        }
    }
}
