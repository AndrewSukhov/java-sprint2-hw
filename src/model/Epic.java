package model;

// Класс Epic описывает сущность задачи типа "эпик"
public class Epic extends Task {

    public Epic(String name, String description, Integer id) {
        super(name, description, id);
    }
}
