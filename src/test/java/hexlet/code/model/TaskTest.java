package hexlet.code.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskTest {

    @Test
    void testGettersAndSetters() {
        Task task = new Task();
        task.setName("Task 1");
        task.setDescription("Description 1");
        task.setIndex(10L);
        task.setCreatedAt(LocalDate.now());

        assertEquals("Task 1", task.getName());
        assertEquals("Description 1", task.getDescription());
        assertEquals(10L, task.getIndex());
        assertNotNull(task.getCreatedAt());
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
    void testAddMultipleLabels() {
        Task task = new Task();
        Label label1 = new Label();
        Label label2 = new Label();

        task.addLabel(label1);
        task.addLabel(label2);

        assertEquals(2, task.getLabels().size());
        assertTrue(label1.getTasks().contains(task));
        assertTrue(label2.getTasks().contains(task));

        task.removeLabel(label1);
        assertEquals(1, task.getLabels().size());
        assertFalse(label1.getTasks().contains(task));
        assertTrue(label2.getTasks().contains(task));
    }

    @Test
    void testAssigneeAndStatusAssignment() {
        Task task = new Task();
        User user = new User();
        TaskStatus status = new TaskStatus();

        task.setAssignee(user);
        task.setTaskStatus(status);

        assertEquals(user, task.getAssignee());
        assertEquals(status, task.getTaskStatus());
    }

    @Test
    void testEqualsAndHashCode() {
        Task t1 = new Task();
        Task t2 = new Task();

        t1.setId(1L);
        t2.setId(1L);

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }
}
