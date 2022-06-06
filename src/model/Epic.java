package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

// Класс Epic описывает сущность задачи типа "эпик"
public class Epic extends Task {
    ArrayList<SubTask> subTasks = new ArrayList<>();


    public Epic(String type, String title, String description, Integer id, Status status) {
        super(type, title, description, id, status);
    }

    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public LocalDateTime getEndTime() {
        for (SubTask subTask:subTasks) {
            endTime.plusMinutes(subTask.getDuration());
        }
        return endTime;
    }
}
