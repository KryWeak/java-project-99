package hexlet.code.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTest {

    @Test
    void testGettersAndSetters() {
        Task task = new Task();
        task.setName("Task 1");
        task.setDescription("Description 1");

        assertEquals("Task 1", task.getName());
        assertEquals("Description 1", task.getDescription());
    }

    @Test
    void testAddAndRemoveLabel() {
        Task task = new Task();
        Label label = new Label();

        task.addLabel(label);
        assertEquals(1, task.getLabels().size());
        assertTrue(label.getTasks().contains(task));

        task.removeLabel(label);
        assertEquals(0, task.getLabels().size());
        assertFalse(label.getTasks().contains(task));
    }

    @Test
    void testSetAssigneeAndStatus() {
        Task task = new Task();
        User user = new User();
        TaskStatus status = new TaskStatus();

        task.setAssignee(user);
        assertEquals(user, task.getAssignee());

        task.setTaskStatus(status);
        assertEquals(status, task.getTaskStatus());
    }
}
