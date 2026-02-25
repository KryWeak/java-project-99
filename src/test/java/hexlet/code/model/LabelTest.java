package hexlet.code.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LabelTest {

    @Test
    void testSizeConstraint() {
        Label label = new Label();
        label.setName("Abc");
        assertEquals("Abc", label.getName());
    }

    @Test
    void testCreatedAt() {
        Label label = new Label();
        label.setCreatedAt(java.time.LocalDate.now());
        assertNotNull(label.getCreatedAt());
    }
}
