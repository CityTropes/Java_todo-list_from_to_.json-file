package todo;

import todo.repository.JSONTodoRepository;

import java.util.List;
import java.util.Scanner;

public class Actions {

    public static void printMainMenu(){
        System.out.println("\nMain menu. Please make a selection, enter: \n0 to run tests,\n1 to create new list with tasks,\n2 to load & edit a list, \n3 to exit.");
        Scanner KeyboardInput = new Scanner(System.in);
        controlsMainMenu(KeyboardInput.next());
    }
    public static void controlsMainMenu(String index){

        switch (index) {
            case "0" -> {
                TestProgram.loadEditSaveTest();
                TestProgram.saveExampleTodoList();
                System.out.println("Test run completed. Check output .json files in data/todos/");
                printMainMenu();
            }
            case "1" -> {
                System.out.println("making new todo list");
                makeNewToDoList();
            }
            case "2" -> {
                System.out.println("loading existing todo list");
                loadListMenu();
            }
            default -> System.out.println("Bye, have a good day!");
        }
            }

     public static void makeNewToDoList(){

        String todosDataPath = "data/todos/";
        JSONTodoRepository newRepo = new JSONTodoRepository(todosDataPath);

        Scanner inputName = new Scanner(System.in);
        System.out.println("Please input a unique name for your to do list: ");

        TodoList newTodo = new TodoList(inputName.nextLine());

        /*just to test
        newTodo.addTask("Test - started list", "First item on list.");
        newTodo.getTaskByIndex(0).complete();*/

        newRepo.save(newTodo);
        System.out.println(newTodo.getName() + " is created and saved (check output .json files in data/todos/).");
        taskbuilder(newTodo.getName());
        printMainMenu();
    }

    public static void taskbuilder(String listName){
        //load file to edit
        String todosDataPath = "data/todos/";
        JSONTodoRepository repo = new JSONTodoRepository(todosDataPath);
        TodoList myToDoList = repo.load(listName);
        Scanner menuChoice = new Scanner(System.in);

        //loop for adding tasks & info
        String index;
        do{
        System.out.println("\nEnter 0 to go previous menu. Enter 1 to add a new task to list '" + listName + "'.");
        index = menuChoice.next();

            if (index.equals("1")) {
                Scanner infoNewTask = new Scanner(System.in);
                System.out.println("Enter title for new task: ");
                String taskName = infoNewTask.nextLine();
                System.out.println("Enter description for task " + taskName);
                String taskDescription = infoNewTask.nextLine();

                myToDoList.addTask(taskName, taskDescription);
                repo.save(myToDoList);
                System.out.println(taskName + " saved to .json.");

            } //else if (index.equals("0")) {
            //loadListMenu();

            //} else {
            //printMainMenu();
            //}
        } while (!index.equals("0"));
    }

    public static void loadListMenu() {

        Scanner getFileName = new Scanner(System.in);
        System.out.println("Enter exact(!) name of existing list (without extension)");
        String loadedFileName = getFileName.nextLine();
        TodoList loadedToDoList = loadListRepo(loadedFileName);
        //try...catch needed

        String index;
        do {
            Scanner menuLoadChoice = new Scanner(System.in);
            System.out.println("Loading menu - list: "+ loadedFileName + "\nEnter 0 to go main menu, \nEnter 1 to see items and desciption from " + loadedToDoList.getName() + ". \nEnter 2 to progress item status from "  + loadedToDoList.getName() + ". \nEnter 3 to add new tasks to "  + loadedToDoList.getName() +  "." );
            index = menuLoadChoice.nextLine();

            switch (index) {
                case "0" -> printMainMenu();
                case "1" -> {
                    List<Task> loadedTasks = loadedToDoList.getAll();
                    for (Task a : loadedTasks) {
                        System.out.println("Title: " + a.getName() + "\nDescription: " + a.getDescription() + "\nStatus: " + a.getStatus() + "\n");
                    }
                }
                case "2" -> {
                    loadedToDoList = progressItemStatus(loadedToDoList);
                    saveListRepo(loadedFileName, loadedToDoList);
                }
                case "3" -> taskbuilder(loadedFileName);
            }
                //update loadedtodolist list loadedTasks

            loadedToDoList = loadListRepo(loadedFileName);
            //List<Task> loadedTasks = loadedToDoList.getAll();
        } while (!index.equals("0"));

    }

    private static void saveListRepo(String loadedFileName, TodoList loadedToDoList) {
        String todosDataPath = "data/todos/";
        JSONTodoRepository saveRepo = new JSONTodoRepository(todosDataPath);

        TodoList todoList;
        todoList = saveRepo.load(loadedFileName); //not needed?

        todoList = loadedToDoList;

        saveRepo.save(todoList);
        System.out.println(loadedFileName + " has been saved to .json.");
    }

    public static TodoList progressItemStatus(TodoList loadedToDoList){
        int i = 0;
        for (Task task : loadedToDoList.getAll()) {
            i++;
            System.out.println("\t"+ i + ". " + task.getName() + " ("+ task.getStatus() +").");
        }
        System.out.println("Choose item number from which you want to progress the status: ");
        Scanner itemChoice = new Scanner(System.in);
        int index = itemChoice.nextInt() -1;

        if (loadedToDoList.getTaskByIndex(index).getStatus() == TaskStatus.PENDING){
            loadedToDoList.getTaskByIndex(index).activate();
            System.out.println(loadedToDoList.getTaskByIndex(index).getName() +": set from 'pending' to '" + loadedToDoList.getTaskByIndex(index).getStatus() +"'.");
        }
        else if (loadedToDoList.getTaskByIndex(index).getStatus() == TaskStatus.ACTIVE){
            loadedToDoList.getTaskByIndex(index).complete();
            System.out.println(loadedToDoList.getTaskByIndex(index).getName() +": set from 'active' to'" + loadedToDoList.getTaskByIndex(index).getStatus() + "'.");
        }
        else if (loadedToDoList.getTaskByIndex(index).getStatus() == TaskStatus.COMPLETED){
            System.out.println(loadedToDoList.getTaskByIndex(index).getName() +": already completed on " + loadedToDoList.getTaskByIndex(index).getCompletedTime());
        }
        return loadedToDoList;
    }

     public static TodoList loadListRepo(String listName){
        //load file to edit
        String todosDataPath = "data/todos/";
        JSONTodoRepository loadedRepo = new JSONTodoRepository(todosDataPath);
        TodoList loadedToDoList = loadedRepo.load(listName);
        System.out.println(loadedToDoList.getName() +" loaded.");
        return loadedToDoList;
    }

}
