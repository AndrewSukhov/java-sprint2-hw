package controller;

import model.Epic;
import model.SubTask;
import model.Task;
import model.Status;

import java.util.List;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Map;

import static controller.TaskTypes.*;
import static model.Status.NEW;

public class FileBackedTasksManager extends InMemoryTasksTaskManager implements TaskManager {

    static File tasksStorage = new File("save.csv");

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public SubTask createSubTask(SubTask subTask, Epic epic) {
        super.createSubTask(subTask, epic);
        save();
        return subTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic;
    }

    @Override
    public Task findTaskById(Integer id) {
        super.findTaskById(id);
        save();
        return taskController.findById(id);
    }

    @Override
    public SubTask findSubTaskById(Integer id) {
        super.findSubTaskById(id);
        save();
        return subTaskController.findById(id);
    }

    @Override
    public Epic findEpicById(Integer id) {
        super.findEpicById(id);
        save();
        return epicController.findById(id);
    }

    @Override
    public Task deleteTaskById(Integer id) {
        final Task deletedTask = super.deleteTaskById(id);
        save();
        return deletedTask;
    }

    @Override
    public void deleteSubTaskById(Integer id) {
        super.deleteSubTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public List<Task> getHistory() {
        final List<Task> history = super.getHistory();
        save();
        return history;
    }

    // Сохранение в файл задач, подзадач, эпиков
    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(tasksStorage.toPath(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Task task : taskController.getTasks().values()) {
                writer.write(task.toString());
                writer.newLine();
            }
            for (SubTask subTask : subTaskController.getSubTasks().values()) {
                writer.write(subTask.toString());
                writer.newLine();
            }
            for (Epic epic : epicController.getEpics().values()) {
                writer.write(epic.toString());
                writer.newLine();
            }

            writer.newLine();

            String historyId = getHistoryId();
            writer.write(historyId);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении в файл");
        }
    }

    // Получение ID из истории.
    static String getHistoryId() {
        List<Integer> listHistoryId = new ArrayList<>();
        for (Map.Entry<Integer, Task> taskId : taskController.getTasks().entrySet()) {
            listHistoryId.add(taskId.getKey());
        }
        for (Map.Entry<Integer, SubTask> subTaskId : subTaskController.getSubTasks().entrySet()) {
            listHistoryId.add(subTaskId.getKey());
        }
        for (Map.Entry<Integer, Epic> epicId : epicController.getEpics().entrySet()) {
            listHistoryId.add(epicId.getKey());
        }
        return listHistoryId.toString().replaceAll("^\\[|\\]$", "");
    }

    // Загрузить данные из файла.
    FileBackedTasksManager loadFromFile(File storageTask) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        try (BufferedReader reader = new BufferedReader(new FileReader(storageTask, StandardCharsets.UTF_8))) {
            boolean readHistory = false;
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.isBlank()) {
                    readHistory = true;
                } else if (readHistory) {
                    break;
                } else {
                    String[] splitter = line.split(", ");
                    TaskTypes type = TaskTypes.valueOf(splitter[0]);
                    String name = splitter[1];
                    String description = splitter[2];
                    Integer id = Integer.parseInt(splitter[3]);
                    Status status = Status.valueOf(splitter[4]);

                    if (type == TASK) {
                        taskController.getTasks().put(id,
                                new Task("TASK", name, description, id, status));
                    } else if (type == SUBTASK) {
                        Integer epicId = Integer.parseInt(splitter[5]);
                        subTaskController.getSubTasks().put(id,
                                new SubTask("SUBTASK", name, description, id, status, epicId));
                    } else if (type == EPIC) {
                        epicController.getEpics().put(id,
                                new Epic("EPIC", name, description, id, status));
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при загрузке из файла");
        }
        return fileBackedTasksManager;
    }


    public static void main(String[] args) {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
//        FileBackedTasksManager.loadFromFile(tasksStorage);// чтобы заработал, сделать static loadFromFile()

        // Проверяем создание и вывод задач
        Task task1 = new Task("TASK","task 1", "desc task 1", 1, NEW);
        fileBackedTasksManager.createTask(task1);
        Task task2 = new Task("TASK","task 2", "desc task 2", 2, NEW);
        fileBackedTasksManager.createTask(task2);
        System.out.println("Выводим все задачи"+ "\n" + fileBackedTasksManager.findAllTasks());

        // Проверяем создание и вывод эпиков
        Epic epic1 = new Epic("EPIC","epic 1", "desc epic 1", 1, NEW);
        fileBackedTasksManager.createEpic(epic1);
        Epic epic2 = new Epic("EPIC", "epic 2", "desc epic 2", 2, NEW);
        fileBackedTasksManager.createEpic(epic2);
        System.out.println("Выводим все эпики"+ "\n" + fileBackedTasksManager.findAllEpics());

        // Проверяем создание и вывод подзадач
        SubTask subtask1 = new SubTask("SUBTASK","subTask 1", "desc subTask 1",
                5, NEW, 1);
        fileBackedTasksManager.createSubTask(subtask1, epic1);
        SubTask subtask2 = new SubTask("SUBTASK","subTask 2", "desc subTask 2",
                6, NEW, 2);
        fileBackedTasksManager.createSubTask(subtask2, epic1);
        SubTask subtask3 = new SubTask("SUBTASK","subTask 3", "desc subTask 3",
                7, NEW, 1);
        fileBackedTasksManager.createSubTask(subtask3, epic1);
        System.out.println("Выводим подзадачи эпика 1" + "\n" + fileBackedTasksManager.findAllSubTasksOfEpic(epic1));

        System.out.println("Выводим пустую историю" + "\n" + fileBackedTasksManager.getHistory());

        // Проверяем историю.
        fileBackedTasksManager.findEpicById(epic1.getId());
        fileBackedTasksManager.findEpicById(epic2.getId());
        fileBackedTasksManager.findEpicById(epic1.getId());
        fileBackedTasksManager.findEpicById(epic2.getId());
        fileBackedTasksManager.findEpicById(epic1.getId());
        fileBackedTasksManager.findEpicById(epic2.getId());
        fileBackedTasksManager.findEpicById(epic1.getId());
        fileBackedTasksManager.findEpicById(epic2.getId());

        System.out.println("Выводим историю" + "\n"
                + fileBackedTasksManager.getHistory());

        // Тестируем удаление по id задачи из истории
        fileBackedTasksManager.deleteTaskById(task1.getId());
        System.out.println("Выводим историю без задачи 1" + "\n"
                + fileBackedTasksManager.getHistory());
    }
}

    
