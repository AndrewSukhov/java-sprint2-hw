import controller.InMemoryTasksManager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        System.out.println("Начинаем тестирование бэк-а");
        InMemoryTasksManager inMemoryTasksManager = new InMemoryTasksManager();

        System.out.println("Метод createTask(task). Начинаем тестирование.");
        final Task task = new Task();
        System.out.println("Создаем 2 задачи");
        final Task createdTask = inMemoryTasksManager.createTask(task);
        final Task createdTask1 = inMemoryTasksManager.createTask(task);
        System.out.println("Печатаем содержание 2х задач");
        System.out.println(createdTask);
        System.out.println(createdTask1);
        if (!task.equals(createdTask) && !createdTask.equals(createdTask1))
            System.out.println("Метод createTask(task) работает штатно");
        else
            System.out.println("Метод createTask(task) работает неправильно");

        System.out.println("Метод findAllTask(). Начинаем тестирование.");
        ArrayList<Task> taskArrayList = inMemoryTasksManager.findAllTasks();
        System.out.println("Метод findAllTask(). Печатаем весь список задач:");
        for (Task value : taskArrayList) {
            System.out.println(value);
        }
        if (taskArrayList.isEmpty()) {
            System.out.println("Метод findAllTask() не возвращает список задач");
        } else {
            System.out.println("Метод findAllTask() работает правильно");
        }

        System.out.println("Метод findTaskById(). Начинаем тестирование.");
        Task foundTask = inMemoryTasksManager.findTaskById(2);
        System.out.println("Печатаем найденную задачу");
        System.out.println(foundTask);
        if (foundTask.getId() != null)
            System.out.println("Метод findById() работает");
        else
            System.out.println("Метод findById() задачу не нашел");

        System.out.println("Метод updateTaskById(). Начинаем тестирование.");
        final Task createdTask2 = inMemoryTasksManager.updateTaskByID(createdTask1);
        System.out.println("Печатам переданную в метод и обновленную задачу:");
        System.out.println(createdTask1);
        System.out.println(createdTask2);
        if (createdTask2.equals(createdTask1))
            System.out.println("Метод updateTaskById() работает");
        else
            System.out.println("Метод updateTaskById() не работает");

        System.out.println("Метод deleteTaskById(). Начинаем тестирование.");
        System.out.println("Печатам удаляемую задачу:");
        System.out.println(inMemoryTasksManager.findTaskById(1));
        inMemoryTasksManager.deleteTaskById(1);
        if (inMemoryTasksManager.findTaskById(1) == null)
            System.out.println("Задача удалена. Метод deleteTaskById() работает правильно.");
        else
            System.out.println("Метод deleteTaskById() не работает");

        System.out.println("Метод deleteAllTask(). Начинаем тестирование.");
        inMemoryTasksManager.deleteAllTask();
        if (inMemoryTasksManager.findAllTasks().isEmpty()) {
            System.out.println("Метод deleteAllTask() работает правильно");
        } else {
            System.out.println("Метод deleteAllTask() не работает");
        }

        System.out.println("Метод findEpic(id). Начинаем тестирование.");
        final Epic epic = new Epic("Эпик", "descriptionOfEpic", -1);
        System.out.println("Создаем 2 эпика");
        final Epic createdEpic = inMemoryTasksManager.createEpic(epic);
        final Epic createdEpic1 = inMemoryTasksManager.createEpic(epic);
        System.out.println("Печатаем содержание 2х задач");
        System.out.println(createdEpic);
        System.out.println(createdEpic1);
        if (!epic.equals(createdEpic) && !createdEpic.equals(createdEpic1))
            System.out.println("Метод createEpic(task) работает штатно");
        else
            System.out.println("Метод createEpic(task) работает неправильно");

        System.out.println("Метод createSubTask(subtask, epic). Начинаем тестирование.");
        final SubTask subTask = new SubTask("Подзадача", "Описание", -1, 1);
        System.out.println("Создаем и печатаем 2 подзадачи одного эпика");
        final SubTask subTask1 = inMemoryTasksManager.createSubTask(subTask, createdEpic);
        final SubTask subTask2 = inMemoryTasksManager.createSubTask(subTask, createdEpic);
        System.out.println(subTask1);
        System.out.println(subTask2);
        System.out.println("Создаем и печатаем 2 подзадачи другого эпика");
        final SubTask subTask3 = inMemoryTasksManager.createSubTask(subTask, createdEpic1);
        final SubTask subTask4 = inMemoryTasksManager.createSubTask(subTask, createdEpic1);
        System.out.println(subTask3);
        System.out.println(subTask4);
        if (subTask1.getEpicID().equals(subTask2.getEpicID()) && subTask3.getEpicID().equals(subTask4.getEpicID()))
            System.out.println("Метод createSubTask(subtask, epic) работает штатно");
        else
            System.out.println("Метод createSubTask(subtask, epic) работает неправильно");

        System.out.println("Метод updateSubTaskById(). Начинаем тестирование.");
        final SubTask subTaskNew = subTask1;

        System.out.println("Печатаем задачу до обновления");
        System.out.println(subTask1);
        subTaskNew.setStatus(Status.DONE);
        inMemoryTasksManager.updateSubTaskByID(subTaskNew);
        System.out.println("Печатаем задачу после обновления");
        System.out.println(subTaskNew);
        System.out.println("Проверяем статус эпика");
        System.out.println(inMemoryTasksManager.findEpicById(1));

        System.out.println("Получение всех подзадач эпика");
        ArrayList<SubTask> listEpics = inMemoryTasksManager.findAllSubTasksOfEpic(inMemoryTasksManager.findEpicById(2));
        System.out.println(listEpics);

        System.out.println("Проверка обновления статуса и удаления подзадач.");
        SubTask subTask5 = new SubTask("Подзадача5", "dfas", 3, 2);
        SubTask subTask6 = new SubTask("Подзадача6", "d2223s", 4, 2);
        subTask5.setStatus(Status.DONE);
        subTask6.setStatus(Status.DONE);
        inMemoryTasksManager.updateSubTaskByID(subTask5);
        inMemoryTasksManager.updateSubTaskByID(subTask6);
        System.out.println(listEpics);
        System.out.println(inMemoryTasksManager.findEpicById(2));
        System.out.println(inMemoryTasksManager.findAllSubTasksOfEpic(inMemoryTasksManager.findEpicById(2)));
        inMemoryTasksManager.deleteSubTaskById(3);
        System.out.println(inMemoryTasksManager.findAllSubTasksOfEpic(inMemoryTasksManager.findEpicById(2)));

        System.out.println("Метод findEpic(epic). Начинаем тестирование.");
        Epic findedEpic = inMemoryTasksManager.findEpicById(1);
        System.out.println("Печатаем найденный эпик");
        System.out.println(findedEpic);
        if (1 == findedEpic.getId()) {
            System.out.println("Метод createEpic(epic) работает штатно");
        } else {
            System.out.println("Метод createEpic(epic) не работает");
        }
    }
}
