package controller;

import model.Epic;

import java.util.ArrayList;
import java.util.HashMap;

// Класс EpicManager содержит список CRUD методов для задач типа "эпик";
public class EpicController {
    HashMap<Integer, Epic> epics = new HashMap<>();
    Integer counterIDEpics = 0;

    // Получение эпика по ID
    public Epic findById(Integer id) {
        return epics.get(id);
    }

    // Получение списка всех эпиков
    public ArrayList<Epic> findAll() {
        return new ArrayList<>(epics.values());
    }

    // Обновление эпика по ID
    public Epic update(Epic epic) {
        final Epic originalTask = epics.get(epic.getId());
        if (originalTask == null) {
            System.out.println("Задачи с таким ID не существует.");
            return null;
        }
        originalTask.setDescription(epic.getDescription());
        originalTask.setName(epic.getName());
        return originalTask;
    }

    //    Создание нового эпика
    public Epic create(Epic task) {
        final Epic newTask = new Epic(task.getName(), task.getDescription(), ++counterIDEpics);
        if (!epics.containsKey(newTask.getId())) {
            epics.put(newTask.getId(), newTask);
        } else {
            System.out.println("Задача с таким ID уже существует");
            return null;
        }
        return newTask;
    }

    // Удаление эпика по идентификатору.
    public void deleteById(Integer id) {
        epics.remove(id);
    }

    // Удаление всех эпиков.
    public void deleteAll() {
        epics.clear();
    }
}
