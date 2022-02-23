package controller;

import model.Task;

import java.util.List;

public interface HistoryManager {
    // Добавление задачи в историю.
    void add(Task task);

    // Удаление задачи из истории.
    void remove(int id);

    // Удаление всей истории.
    void removeAll();

    // Получение истории.
    List<Task> getHistory();
}
