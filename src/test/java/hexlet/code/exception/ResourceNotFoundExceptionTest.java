package hexlet.code.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        ResourceNotFoundException ex =
                new ResourceNotFoundException("Not found");

        assertEquals("Not found", ex.getMessage());
        assertTrue(ex instanceof RuntimeException);
    }
}
