package model;

import java.util.ArrayList;

// Класс Epic описывает сущность задачи типа "эпик"
public class Epic extends Task {
    ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }
}
