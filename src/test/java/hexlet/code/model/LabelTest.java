package hexlet.code.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        label.setCreatedAt(LocalDate.now());

        assertNotNull(label.getCreatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        Label l1 = new Label();
        Label l2 = new Label();

        l1.setId(1L);
        l2.setId(1L);

        assertEquals(l1, l2);
        assertEquals(l1.hashCode(), l2.hashCode());
    }
}
