package controller;

import org.junit.jupiter.api.BeforeEach;

class InMemoryTasksTaskManagerTest extends TaskManagerTest<InMemoryTasksTaskManager>{

    @BeforeEach
    void setUpInMemory() {
        taskManager = new InMemoryTasksTaskManager();
        super.setUp();
    }
}
