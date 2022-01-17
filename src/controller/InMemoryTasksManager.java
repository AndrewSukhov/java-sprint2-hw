package controller;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Класс controller.InMemoryTasksManager содержит список CRUD методов для всех типов задач.
public class InMemoryTasksManager implements Manager {

    private final List<Task> history = new LinkedList<>();

    TaskManager taskManager = new TaskManager();
    EpicManager epicManager = new EpicManager();
    SubTaskManager subTaskManager = new SubTaskManager(epicManager);


    //    Получение списка всех задач.
    @Override
    public ArrayList<Task> findAllTasks() {
        return taskManager.findAll();
    }

    //    Получение списка всех эпиков.
    @Override
    public ArrayList<Epic> findAllEpics() {
        return epicManager.findAll();
    }

    // Получение списка всех подзадач определённого эпика.
    @Override
    public ArrayList<SubTask> findAllSubTasksOfEpic(Epic epic) {
        return subTaskManager.findAllOfEpic(epic);
    }

    // Получение подзадачи по идентификатору
    @Override
    public SubTask findSubTaskById(Integer id) {
        final SubTask subTask = subTaskManager.findById(id);
        addInHistory(subTask);
        return subTask;
    }

    // Получение задачи по идентификатору
    @Override
    public Task findTaskById(Integer id) {
        final Task task = taskManager.findById(id);
        addInHistory(task);
        return task;
    }

    // Получение эпика по идентификатору
    @Override
    public Epic findEpicById(Integer id) {
        final Epic epic = epicManager.findById(id);
        addInHistory(epic);
        return epic;
    }

    // Добавление задачи.
    @Override
    public Task createTask(Task task) {
        return taskManager.create(task);
    }

    // Добавление подзадачи.
    @Override
    public SubTask createSubTask(SubTask subTask, Epic epic) {
        return subTaskManager.create(subTask, epic);
    }

    // Добавление Эпика.
    @Override
    public Epic createEpic(Epic epic) {
        return epicManager.create(epic);
    }

    // Обновление задачи.
    @Override
    public Task updateTaskByID(Task task) {
        return taskManager.update(task);
    }

    // Обновление подзадачи.
    @Override
    public SubTask updateSubTaskByID(SubTask subTask) {
        return subTaskManager.update(subTask);
    }

    // Обновление эпика.
    @Override
    public Task updateEpicByID(Epic epic) {
        return epicManager.update(epic);
    }

    // Удаление всех задач.
    @Override
    public void deleteAllTask() {
        taskManager.deleteAll();
    }

    // Удаление всех подзадач.
    @Override
    public void deleteAllSubTasks() {
        subTaskManager.deleteAll();
    }

    // Удаление всех эпиков.
    @Override
    public void deleteAllEpics() {
        epicManager.deleteAll();
    }

    // Удаление подзадач по ID.
    @Override
    public void deleteSubTaskById(Integer id) {
        subTaskManager.deleteById(id);
    }

    // Удаление эпика по ID.
    @Override
    public void deleteEpicById(Integer id) {
        epicManager.deleteById(id);
    }

    // Удаление задачи по ID.
    @Override
    public Task deleteTaskById(Integer id) {
        return taskManager.deleteById(id);
    }

    // Сохранение последних просмотренных задач.
    @Override
    public List<Task> history() {
        return history;
    }

    private void addInHistory(Task task) {
        if (task == null) {
            return;
        }
        if (history.size() == 10) {
            history.remove(0);
        }
        history.add(task);
    }

}
