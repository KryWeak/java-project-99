package hexlet.code.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LabelTest {

    @Test
    void testGettersAndSetters() {
        Label label = new Label();
        label.setName("Urgent");

        assertEquals("Urgent", label.getName());
    }
}
