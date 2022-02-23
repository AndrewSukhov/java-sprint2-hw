package controller;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Класс controller.InMemoryTasksTaskManager содержит список CRUD методов для всех типов задач.
public class InMemoryTasksTaskManager implements TaskManager {

    private final List<Task> history = new LinkedList<>();

    TaskController taskController = new TaskController();
    EpicController epicController = new EpicController();
    SubTaskController subTaskController = new SubTaskController(epicController);
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    //    Получение списка всех задач.
    @Override
    public ArrayList<Task> findAllTasks() {
        return taskController.findAll();
    }

    //    Получение списка всех эпиков.
    @Override
    public ArrayList<Epic> findAllEpics() {
        return epicController.findAll();
    }

    // Получение списка всех подзадач определённого эпика.
    @Override
    public ArrayList<SubTask> findAllSubTasksOfEpic(Epic epic) {
        return subTaskController.findAllOfEpic(epic);
    }

    // Получение подзадачи по идентификатору
    @Override
    public SubTask findSubTaskById(Integer id) {
        final SubTask subTask = subTaskController.findById(id);
        addInHistory(subTask);
        return subTask;
    }

    // Получение задачи по идентификатору
    @Override
    public Task findTaskById(Integer id) {
        final Task task = taskController.findById(id);
        addInHistory(task);
        return task;
    }

    // Получение эпика по идентификатору
    @Override
    public Epic findEpicById(Integer id) {
        final Epic epic = epicController.findById(id);
        addInHistory(epic);
        return epic;
    }

    // Добавление задачи.
    @Override
    public Task createTask(Task task) {
        return taskController.create(task);
    }

    // Добавление подзадачи.
    @Override
    public SubTask createSubTask(SubTask subTask, Epic epic) {
        return subTaskController.create(subTask, epic);
    }

    // Добавление Эпика.
    @Override
    public Epic createEpic(Epic epic) {
        return epicController.create(epic);
    }

    // Обновление задачи.
    @Override
    public Task updateTaskByID(Task task) {
        return taskController.update(task);
    }

    // Обновление подзадачи.
    @Override
    public SubTask updateSubTaskByID(SubTask subTask) {
        return subTaskController.update(subTask);
    }

    // Обновление эпика.
    @Override
    public Task updateEpicByID(Epic epic) {
        return epicController.update(epic);
    }

    // Удаление всех задач.
    @Override
    public void deleteAllTask() {
        taskController.deleteAll();
    }

    // Удаление всех подзадач.
    @Override
    public void deleteAllSubTasks() {
        subTaskController.deleteAll();
    }

    // Удаление всех эпиков.
    @Override
    public void deleteAllEpics() {
        epicController.deleteAll();
    }

    // Удаление подзадач по ID.
    @Override
    public void deleteSubTaskById(Integer id) {
        inMemoryHistoryManager.remove(id);
        subTaskController.deleteById(id);
    }

    // Удаление эпика по ID.
    @Override
    public void deleteEpicById(Integer id) {
        inMemoryHistoryManager.remove(id);
        epicController.deleteById(id);
    }

    // Удаление задачи по ID.
    @Override
    public Task deleteTaskById(Integer id) {
        inMemoryHistoryManager.remove(id);
        return taskController.deleteById(id);
    }

    // Сохранение последних просмотренных задач.
    @Override
    public List<Task> history() {
        return history;
    }

//    private void addInHistory(Task task) {
//        if (task == null) {
//            return;
//        }
//        if (history.size() == 10) {
//            history.remove(0);
//        }
//        history.add(task);
//    }

    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    public void removeAllHistory() {
        inMemoryHistoryManager.removeAll();
    }

    public void addInHistory(Task task) {
        inMemoryHistoryManager.add(task);
    }

    public void removeFromHistoryById(int id) {
        inMemoryHistoryManager.remove(id);
    }
}
