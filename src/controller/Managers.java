package controller;

public class Managers {
    public  static TaskManager getDefault() {
        return new InMemoryTasksTaskManager();
    }
}
