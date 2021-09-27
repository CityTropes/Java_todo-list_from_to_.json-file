package todo;

import java.util.ArrayList;
import java.util.List;

public final class TodoList {

    private String name;
    private List<Task> tasks;

    public TodoList(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public TodoList(String name) {
        this(name, new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public Task addTask(String name) {

        return addTask(name, "");
    }

    public Task addTask(String name, String description) {
        Task task = new Task(name, description);
        tasks.add(task);
        return task;
    }

    public Task getTaskByIndex(int index) {
        return tasks.get(index);
    }

    public List<Task> getAll() {
        return tasks;
    }
}
