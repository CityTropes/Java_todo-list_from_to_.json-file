package todo.repository;

import org.json.JSONArray;
import org.json.JSONObject;
import todo.Task;
import todo.TaskStatus;
import todo.TodoList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JSONTodoRepository {

    private String directoryPath;


    public JSONTodoRepository(String directoryPath) {
        File file = new File(directoryPath);
        this.directoryPath = file.getAbsolutePath();
    }


    public void save(TodoList todoList) {
        JSONObject json = toJson(todoList);
        Path filePath = Path.of(directoryPath, todoList.getName() + ".json");
        try {
            Files.writeString(filePath, json.toString(4));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }



    public TodoList load(String name) {
        Path filePath = Path.of(directoryPath, name + ".json");
        String jsonString;
        try {
            jsonString = Files.readString(filePath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        JSONObject json = new JSONObject(jsonString);

        return fromJson(json);
    }



    private JSONObject toJson(TodoList todoList) {
        JSONObject json = new JSONObject();
        json.put("name", todoList.getName());
        JSONArray jsonTaskList = new JSONArray();
        for (Task task : todoList.getAll()) {
            JSONObject jsonTask = new JSONObject();
            jsonTask.put("name", task.getName());
            jsonTask.put("description", task.getDescription());
            jsonTask.put("status", task.getStatus());
            jsonTask.put("completedTime", task.getCompletedTime());
            jsonTaskList.put(jsonTask);
        }
        json.put("tasks", jsonTaskList);

        return json;
    }


    private TodoList fromJson(JSONObject json) {
        List<Task> tasks = new ArrayList<>();
        JSONArray jsonTasks = json.getJSONArray("tasks");
        for (int i = 0; i < jsonTasks.length(); i++) {
            JSONObject jsonTask = jsonTasks.getJSONObject(i);
            LocalDateTime completedTime = null;
            if (jsonTask.has("completedTime")) {
                completedTime = LocalDateTime.parse(jsonTask.getString("completedTime"));
            }
            tasks.add(new Task(
                jsonTask.getString("name"),
                jsonTask.getString("description"),
                TaskStatus.valueOf(jsonTask.getString("status")),
                completedTime
            ));
        }

        return new TodoList(json.get("name").toString(), tasks);
    }

}
