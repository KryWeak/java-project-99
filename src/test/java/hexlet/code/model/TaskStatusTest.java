package hexlet.code.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TaskStatusTest {

    @Test
    void testGettersAndSetters() {
        TaskStatus status = new TaskStatus();
        status.setName("Open");
        status.setSlug("open");

        assertEquals("Open", status.getName());
        assertEquals("open", status.getSlug());
    }

    @Test
    void testAddAndRemoveTask() {
        TaskStatus status = new TaskStatus();
        Task task = new Task();

        status.addTask(task);
        assertEquals(1, status.getTasks().size());
        assertEquals(status, task.getTaskStatus());

        status.removeTask(task);
        assertEquals(0, status.getTasks().size());
        assertNull(task.getTaskStatus());
    }
}
