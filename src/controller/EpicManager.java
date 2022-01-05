package controller;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

// Класс EpicManager содержит список CRUD методов для задач типа "эпик";
public class EpicManager {
    HashMap<Integer, Task> epics = new HashMap<>();
    Integer counterIDEpics = 0;

    // Получение эпика по ID
    public Task findById(Integer id) {
        return epics.get(id);
    }

    // Получение списка всех эпиков
    public ArrayList<Task> findAll() {
        return (ArrayList<Task>) epics.values();
    }

    // Обновление эпика по ID
    public Task update(Task task) {
        final Task originalTask = epics.get(task.getId());
        if (originalTask == null) {
            System.out.println("Задачи с таким ID не существует.");
            return null;
        }
        originalTask.setDescription(task.getDescription());
        originalTask.setName(task.getName());
        return originalTask;
    }

    //    Создание нового эпика
    public Task create(Task task) {
        final Task newTask = new Task(task.getName(), task.getDescription(), ++counterIDEpics);
        if (!epics.containsKey(newTask.getId()))
            epics.put(newTask.getId(), newTask);
        else {
            System.out.println("Задача с таким ID уже существует");
            return null;
        }
        return newTask;
    }

    // Удаление эпика по идентификатору.
    public Task deleteById(Integer id) {
        final Task deletedTask = epics.get(id);
        epics.remove(id);
        return deletedTask;
    }

    // Удаление всех эпиков.
    public void deleteAll() {
        epics.clear();
    }
}
