package hexlet.code.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LabelTest {

    @Test
    void testGettersAndSetters() {
        Label label = new Label();
        label.setName("Urgent");

        assertEquals("Urgent", label.getName());
    }

    @Test
    void testTasksAssociation() {
        Label label = new Label();
        Task task = new Task();

        task.addLabel(label);

        assertTrue(label.getTasks().contains(task));
        assertTrue(task.getLabels().contains(label));
        task.removeLabel(label);

        assertFalse(label.getTasks().contains(task));
        assertFalse(task.getLabels().contains(label));
    }

    @Test
    void testDefaultTasksListIsEmpty() {
        Label label = new Label();
        assertNotNull(label.getTasks());
        assertTrue(label.getTasks().isEmpty());
    }
}
