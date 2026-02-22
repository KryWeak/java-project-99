package hexlet.code.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@test.com");
        user.setPassword("secret");

        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@test.com", user.getEmail());
        assertEquals("secret", user.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("john.doe@test.com");

        User user2 = new User();
        user2.setId(1L);
        user2.setEmail("john.doe@test.com");

        User user3 = new User();
        user3.setId(2L);
        user3.setEmail("jane.doe@test.com");

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);

        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testToString() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@test.com");

        String str = user.toString();
        assertEquals(true, str.contains("John"));
        assertEquals(true, str.contains("Doe"));
        assertEquals(true, str.contains("john.doe@test.com"));
    }

    @Test
    void testAddAndRemoveTask() {
        User user = new User();
        Task task = new Task();

        user.addTask(task);
        assertEquals(1, user.getTasks().size());
        assertEquals(user, task.getAssignee());

        user.removeTask(task);
        assertEquals(0, user.getTasks().size());
        assertEquals(null, task.getAssignee());
    }

    @Test
    void testUserDetailsMethods() {
        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("pass");

        assertEquals("pass", user.getPassword());
        assertEquals("john@example.com", user.getUsername());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
        assertTrue(user.getAuthorities().isEmpty());
    }
}
