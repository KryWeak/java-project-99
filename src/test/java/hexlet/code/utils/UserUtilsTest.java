package hexlet.code.utils;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserUtilsTest {

    @Autowired
    private UserUtils userUtils; // Spring создаёт и внедряет userRepository

    @MockBean
    private UserRepository repository; // мокируем репозиторий для теста

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testIsCurrentUser() {
        User user = new User();
        user.setEmail("user@test.com");

        when(repository.findById(1L)).thenReturn(Optional.of(user));

        TestingAuthenticationToken auth =
                new TestingAuthenticationToken("user@test.com", null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        boolean result = userUtils.isCurrentUser(1L);

        assertTrue(result);
    }
}
