package hexlet.code.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthRequestTest {

    @Test
    void testGettersAndSetters() {
        AuthRequest request = new AuthRequest();
        request.setUsername("user@test.com");
        request.setPassword("secret");

        assertEquals("user@test.com", request.getUsername());
        assertEquals("secret", request.getPassword());
    }
}
