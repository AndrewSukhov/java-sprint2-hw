package controller;

public class Managers {
    public  static Manager getDefault() {
        return new InMemoryTasksManager();
    }
}
