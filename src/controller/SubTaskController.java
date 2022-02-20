package controller;

import model.Epic;
import model.Status;
import model.SubTask;

import java.util.ArrayList;
import java.util.HashMap;

// Класс SubTaskManager содержит список CRUD методов для задач типа "подзадача";
public class SubTaskController {
    private Integer counterIDSubTasks = 0;
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    EpicController epicController;

    public SubTaskController(EpicController epicController) {
        this.epicController = epicController;
    }

    // Добавить подзадачу.
    public SubTask create(SubTask subTask, Epic epic) {
        final SubTask newSubTask = new SubTask(subTask.getName(), subTask.getDescription(), ++counterIDSubTasks, epic.getId());
        if (!subTasks.containsKey(newSubTask.getId())) {
            subTasks.put(newSubTask.getId(), newSubTask);
            epicController.epics.get(epic.getId()).getSubTasks().add(/*newSubTask.getId(),*/ newSubTask);
        } else {
            System.out.println("Задача с таким ID уже существует");
            return null;
        }
        return newSubTask;
    }

    // Получить подзадачу по ID
    public SubTask findById(Integer id) {
        return subTasks.get(id);
    }

    // Обновление подзадачи по ID
    public SubTask update(SubTask task) {
        final SubTask originalTask = subTasks.get(task.getId());
        if (originalTask == null) {
            System.out.println("Задачи с таким ID не существует.");
            return null;
        }
        originalTask.setDescription(task.getDescription());
        originalTask.setName(task.getName());
        originalTask.setStatus(task.getStatus());
        epicController.epics.get(task.getEpicID()).getSubTasks().remove(originalTask);
        epicController.epics.get(task.getEpicID()).getSubTasks().add(task);
        refreshStatus(task);
        return originalTask;
    }

    // Удаление задачи по идентификатору.
    public SubTask deleteById(Integer id) {
        final SubTask deletedTask = subTasks.get(id);
        epicController.epics.get(deletedTask.getEpicID()).getSubTasks().remove(deletedTask);
        subTasks.remove(id);
        return deletedTask;
    }

    // Удаление всех задач.
    public void deleteAll() {
        subTasks.clear();
    }

    // Получение списка всех подзадач определённого эпика.
    public ArrayList<SubTask> findAllOfEpic(Epic epic) {
        return epicController.epics.get(epic.getId()).getSubTasks();
    }

    // Обновление статуса эпика в зависимости от статуса подзадач
    public void refreshStatus(SubTask task) {
        ArrayList<SubTask> subTasksOfEpic = epicController.epics.get(task.getEpicID()).getSubTasks();
        int counterNew = 0;
        int counterDone = 0;
        for (SubTask subTask : subTasksOfEpic) {
            if (subTask.getStatus() == Status.NEW) {
                counterNew++;
            } else if (subTask.getStatus() == Status.DONE) {
                counterDone++;
            }
        }
        if (counterNew == subTasksOfEpic.size()) {
            epicController.epics.get(task.getEpicID()).setStatus(Status.NEW);
        } else if (counterDone == subTasksOfEpic.size()) {
            epicController.epics.get(task.getEpicID()).setStatus(Status.DONE);
        } else {
            epicController.epics.get(task.getEpicID()).setStatus(Status.IN_PROGRESS);
        }
    }
}
