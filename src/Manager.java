import controller.EpicManager;
import controller.SubTaskManager;
import controller.TaskManager;
import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;

// Класс Manager содержит список CRUD методов для всех типов задач.
public class Manager {

    TaskManager taskManager = new TaskManager();
    EpicManager epicManager = new EpicManager();
    SubTaskManager subTaskManager = new SubTaskManager();


    //    Получение списка всех задач.
    public ArrayList<Task> findAllTasks() {
        return taskManager.findAll();
    }

    //    Получение списка всех эпиков.
    public ArrayList<Task> findAllEpics() {
        return epicManager.findAll();
    }

    // Получение списка всех подзадач определённого эпика.
    public ArrayList<SubTask> findAllSubTasksOfEpic(Epic epic) {
        return subTaskManager.findAllOfEpic(epic);
    }

    // Получение подзадачи по идентификатору
    public SubTask findSubTaskById(Integer id) {
        return subTaskManager.findById(id);
    }

    // Получение задачи по идентификатору
    public Task findTaskById(Integer id) {
        return taskManager.findById(id);
    }

    // Получение эпика по идентификатору
    public Task findEpicById(Integer id) {
        return epicManager.findById(id);
    }

    // Добавление задачи.
    public Task createTask(Task task) {
        return taskManager.create(task);
    }

    // Добавление подзадачи.
    public SubTask createSubTask(SubTask subTask, Epic epic) {
        return subTaskManager.create(subTask, epic);
    }

    // Добавление Эпика.
    public Task createEpic(Epic epic) {
        return epicManager.create(epic);
    }

    // Обновление задачи.
    public Task updateTask(Task task) {
        return taskManager.update(task);
    }

    // Обновление подзадачи.
    public SubTask updateSubTask(SubTask subTask) {
        return subTaskManager.update(subTask);
    }

    // Обновление эпика.
    public Task updateEpic(Epic epic) {
        return epicManager.update(epic);
    }

    // Удаление всех задач.
    public void deleteAllTask() {
        taskManager.deleteAll();
    }

    // Удаление всех подзадач.
    public void deleteAllSubTasks() {
        subTaskManager.deleteAll();
    }

    // Удаление всех эпиков.
    public void deleteAllEpics() {
        epicManager.deleteAll();
    }

    // Удаление подзадач по ID.
    public void deleteSubTaskById(Integer id) {
        subTaskManager.deleteById(id);
    }

    // Удаление эпика по ID.
    public void deleteEpicById(Integer id) {
        epicManager.deleteById(id);
    }

    // Удаление задачи по ID.
    public Task deleteTaskById(Integer id) {
        return taskManager.deleteById(id);
    }
}
