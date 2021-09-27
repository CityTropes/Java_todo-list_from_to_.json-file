package todo;

import todo.repository.JSONTodoRepository;

public class TestProgram {

    /* for testing purposes below */

    public static void loadEditSaveTest(){

        String todosDataPath = "data/todos/";
        JSONTodoRepository repo = new JSONTodoRepository(todosDataPath);

        TodoList todoList;
        todoList = repo.load("TestLoadSaveList");

        Task lastTask = todoList.addTask("Running tests...");
        lastTask.activate();
        lastTask = todoList.addTask("Completing Test", "this is a test");
        lastTask.complete();

        repo.save(todoList);
    }

    public static void saveExampleTodoList() {
        String todosDataPath = "data/todos/";
        JSONTodoRepository testRepo = new JSONTodoRepository(todosDataPath);

        TodoList todo = new TodoList("ExampleTodoList");

        todo.addTask("Cleaning", "Dishes");
        todo.addTask("Washing", "Clothes");
        todo.addTask("Shopping", "");

        todo.getTaskByIndex(0).complete();
        todo.getTaskByIndex(1).activate();

        testRepo.save(todo);

        // return todo;
    }

}
