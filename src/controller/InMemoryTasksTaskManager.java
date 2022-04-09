package controller;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

// Класс controller.InMemoryTasksTaskManager содержит список CRUD методов для всех типов задач.
public class InMemoryTasksTaskManager implements TaskManager {

    static TaskController taskController = new TaskController();
    static EpicController epicController = new EpicController();
    static SubTaskController subTaskController = new SubTaskController(epicController);
    static InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

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
        // Если удаляемые задачи присутствуют в истории, то очищаем и их.
        if(!inMemoryHistoryManager.getMap().isEmpty()){
            for (var historyTask : inMemoryHistoryManager.getMap().values()) {
                for (var task: taskController.getTasks().values()) {
                    if (task.equals(historyTask.task)) {
                        inMemoryHistoryManager.remove(historyTask.task.getId());
                    }
                }
            }
        }
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
        removeFromHistoryById(id);
        subTaskController.deleteById(id);
    }

    // Удаление эпика по ID.
    @Override
    public void deleteEpicById(Integer id) {
        removeFromHistoryById(id);
        epicController.deleteById(id);
    }

    // Удаление задачи по ID.
    @Override
    public Task deleteTaskById(Integer id) {
        removeFromHistoryById(id);
        return taskController.deleteById(id);
    }

    // Получение истории.
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    // Удаление всей истории.
    public void removeAllHistory() {
        inMemoryHistoryManager.removeAll();
    }

    // Добавление задачи в историю.
    public void addInHistory(Task task) {
        inMemoryHistoryManager.add(task);
    }

    // Удаление задачи из истории по ИД.
    public void removeFromHistoryById(int id) {
        inMemoryHistoryManager.remove(id);
    }
}
