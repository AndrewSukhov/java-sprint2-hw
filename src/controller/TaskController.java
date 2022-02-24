package controller;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

// Класс TaskManager содержит список CRUD методов для задач типа "задача";
public class TaskController {
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private Integer counterIDTasks = 0;

    // Получение списка всех задач
    public ArrayList<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    // Получение задачи по ID
    public Task findById(Integer id) {
        return tasks.get(id);
    }

    // Обновление задачи по ID
    public Task update(Task task) {
        final Task originalTask = tasks.get(task.getId());
        if (originalTask == null) {
            System.out.println("Задачи с таким ID не существует.");
            return null;
        }
        originalTask.setDescription(task.getDescription());
        originalTask.setName(task.getName());
        originalTask.setStatus(task.getStatus());
        return originalTask;
    }

    //    Создание новой задачи
    public Task create(Task task) {
        final Task newTask = new Task(task.getName(), task.getDescription(), ++counterIDTasks);
        if (!tasks.containsKey(newTask.getId()))
            tasks.put(newTask.getId(), newTask);
        else {
            System.out.println("Задача с таким ID уже существует");
            return null;
        }
        return newTask;
    }

    // Удаление задачи по идентификатору.
    public Task deleteById(Integer id) {
        final Task deletedTask = tasks.get(id);
        tasks.remove(id);
        return deletedTask;
    }

    // Удаление всех задач.
    public void deleteAll() {
        tasks.clear();
    }
}
