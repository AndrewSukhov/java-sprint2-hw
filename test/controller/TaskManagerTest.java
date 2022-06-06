package controller;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected SubTask subTaskNEW;
    protected SubTask subTaskIN_PROGRESS;
    protected SubTask subTaskDONE;

    void setUp() {
        task = taskManager.createTask(new Task("TaskName", "TaskDescript", 1,
                Status.NEW, "TASK", LocalDateTime.now(), 10));
        epic =  taskManager.createEpic(new Epic("EPIC", "EpicName",
                "EpicDescript", 1, Status.NEW));
        subTaskNEW = taskManager.createSubTask((new SubTask("SUBTASK", "SubTaskName",
                "SubTaskDescript", 1, Status.NEW, 1)), epic);
        subTaskIN_PROGRESS = taskManager.createSubTask((new SubTask("SUBTASK", "SubTaskName",
                "SubTaskDescript", 1, Status.IN_PROGRESS, 1)), epic);
        subTaskDONE = taskManager.createSubTask((new SubTask("SUBTASK", "SubTaskName",
                "SubTaskDescript", 1, Status.DONE, 1)), epic);
    }

    @Test
    void findAllTasks() {
        assertNotNull(taskManager.findAllTasks(), "Задачи на возвращаются.");
        assertEquals(taskManager.findTaskById(1).getType(), "TASK", "Возвращена не задача");
    }

    @Test
    void findAllEpics() {
        assertNotNull(taskManager.findAllEpics(), "Эпики на возвращаются.");
        assertEquals(taskManager.findEpicById(1).getType(), "EPIC", "Возвращен не эпик");
    }

    @Test
    void findAllSubTasksOfEpic() {
        List<SubTask> subTasks = taskManager.findAllSubTasksOfEpic(epic);
        assertNotNull(subTasks, "Подзадачи на возвращаются.");
    }

    @Test
    void findSubTaskById() {
        SubTask subTask = taskManager.findSubTaskById(1);
        SubTask subTask1 = taskManager.findSubTaskById(10001);
        assertEquals(1, subTask.getId(),  "ИД не совпадают");
        assertNull(subTask1, "Задача существует");
    }

    @Test
    void findTaskById() {
        Task task = taskManager.findTaskById(1);
        Task task1 = taskManager.findTaskById(10001);
        assertEquals(1, task.getId(),  "ИД не совпадают");
        assertNull(task1, "Задача существует");
    }

    @Test
    void findEpicById() {
        Epic task = taskManager.findEpicById(1);
        Epic task1 = taskManager.findEpicById(10001);
        assertEquals(1, task.getId(),  "ИД не совпадают");
        assertNull(task1, "Задача существует");
    }

    @Test
    void createTask() {
        final Task savedTask = taskManager.findTaskById(1);
        assertNotNull(savedTask, "Задача не найдена.");
        final List<Task> tasks = taskManager.findAllTasks();
        assertNotNull(tasks, "Задачи на возвращаются.");
    }

    @Test
    void createSubTask() {
        SubTask subTask = taskManager.findSubTaskById(1);
         int epicID = subTask.getEpicID();
         Epic epic = taskManager.findEpicById(epicID);
         assertNotNull(epic, "Эпик не найден");
    }

    @Test
    void createEpic() {
        Epic task = taskManager.findEpicById(1);
        assertNotNull(task, "Эпик не найден");
    }

    @Test
    void updateTaskByID() {
        Task task1 = taskManager.createTask(new Task("TaskName", "TaskDescript", 1,
                Status.NEW, "TASK", LocalDateTime.now(), 10));
        task = taskManager.updateTaskByID(task1);
        assertEquals(task1, task, "задача не обновилась");
    }

    @Test
    void updateSubTaskByID() {
        SubTask subTaskIN_PROGRESS1 = taskManager.createSubTask((new SubTask("SUBTASK", "SubTaskName",
                "SubTaskDescript", 1, Status.IN_PROGRESS, 1)), epic);
        SubTask subTask = taskManager.updateSubTaskByID(subTaskIN_PROGRESS1);
        assertEquals(subTask.getStatus(), subTaskIN_PROGRESS1.getStatus(), "Подзадача не обновилась");
    }

    @Test
    void updateEpicByID() {
        Epic epicDONE =  taskManager.createEpic(new Epic("EPIC", "EpicNameDONE",
                "EpicDescriptDONE", 1, Status.DONE));
        epic = (Epic) taskManager.updateEpicByID(epicDONE);
        assertEquals(epicDONE.getDescription(), epic.getDescription(), "Эпик не обновился");
    }
/*
  @Test
    void deleteAllTask() {
        taskManager.deleteAllTask();
        assertEquals(0, taskManager.findAllTasks().size(), "Задачи не удалены");
    }

    @Test
    void deleteAllSubTasks() {
        taskManager.deleteAllSubTasks();
        assertEquals(null, taskManager.findSubTaskById(1), "Подзадачи не удалены");
    }

    @Test
    void deleteAllEpics() {
        taskManager.deleteAllEpics();
        assertEquals(0, taskManager.findAllEpics().size(), "Эпики не удалены");
    }

    @Test
    void deleteSubTaskById() {
        taskManager.deleteSubTaskById(1);
       SubTask subTask = taskManager.findSubTaskById(1);
        assertEquals(null, taskManager.findSubTaskById(1), "Задача не удалена");
    }

    @Test
    void deleteEpicById() {
        Epic epic2 =  taskManager.createEpic(new Epic("EPIC", "EpicName",
                "EpicDescript", 2, Status.NEW));
        taskManager.createEpic(epic2);
        assertNotNull(taskManager.findEpicById(2), "Задачи не существует");
        taskManager.deleteEpicById(2);
        assertEquals(null, taskManager.findEpicById(2), "Задача не удалена");
    }


    @Test
    void deleteTaskById() {
        assertNotNull(taskManager.findTaskById(5), "Задачи не существует");
        taskManager.deleteTaskById(5);
        assertNull(taskManager.findTaskById(5), "Задача не удалена");
    }
*/
    @Test
    void removeFromHistoryById() {
        List<Task> tasksHist = taskManager.getHistory();
        taskManager.removeFromHistoryById(1);
        List<Task> tasksHist1 = taskManager.getHistory();
        assertFalse(tasksHist.size() == tasksHist1.size(), "История не изменилась");
    }

    @Test
    void getHistory() {
        List<Task> tasksHist = taskManager.getHistory();
        assertNotNull(tasksHist, "История не получена или пустая");
    }

    @Test
    void addInHistory() {
        List<Task> tasksHist = taskManager.getHistory();
        Task task1 = taskManager.createTask(new Task("TaskName2", "TaskDescript", 2,
                Status.NEW, "TASK", LocalDateTime.now(), 10));
        Task t = taskManager.findTaskById(1);
        taskManager.addInHistory(task1);
        List<Task> tasksHist1 = taskManager.getHistory();
        assertTrue(tasksHist.size() != tasksHist1.size(), "История не изменилась");
    }

    @Test
    void removeAllHistory() {
        taskManager.removeAllHistory();
        List<Task> tasksHist1 = taskManager.getHistory();
        assertTrue(tasksHist1.size() == 0, "История не пустая");
    }
}