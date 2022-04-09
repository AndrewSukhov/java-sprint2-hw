package controller;

public enum TaskTypes {
    TASK("TASK"),
    SUBTASK("SUBTASK"),
    EPIC("EPIC");

    private final String value;

    TaskTypes(String value) {
        this.value = value;
    }
}
