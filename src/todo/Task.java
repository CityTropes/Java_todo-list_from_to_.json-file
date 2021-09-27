package todo;

import java.time.LocalDateTime;

public class Task {

    private String name;
    private String description;
    private TaskStatus status = TaskStatus.PENDING;
    private LocalDateTime completedTime;

    public Task(String name, String description, TaskStatus status, LocalDateTime completedTime) {
        this.name = name;
        this.description = description != null ? description : ""; //if statement
        this.status = status;
        this.completedTime = completedTime;
    }

    public Task(String name, String description) {
        this(name, description, TaskStatus.PENDING, null);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCompletedTime() {
        return completedTime;
    }

    public void complete() {
        status = TaskStatus.COMPLETED;
        completedTime = LocalDateTime.now();
    }

    public void activate() {
        status = TaskStatus.ACTIVE;
    }


}
