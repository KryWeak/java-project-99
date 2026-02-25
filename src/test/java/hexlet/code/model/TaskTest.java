package hexlet.code.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskTest {
    @Test
    void testIndexSetterGetter() {
        Task task = new Task();
        task.setIndex(5L);
        assertEquals(5L, task.getIndex());
    }

    @Test
    void testCreatedAt() {
        Task task = new Task();
        task.setCreatedAt(java.time.LocalDate.now());
        assertNotNull(task.getCreatedAt());
    }
}
