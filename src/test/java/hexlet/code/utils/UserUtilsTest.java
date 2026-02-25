package hexlet.code.utils;

import hexlet.code.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;

class UserUtilsNewTest {

    private final UserUtils userUtils = new UserUtils();

    @Test
    void testGetCurrentUserWhenNotAuthenticated() {
        SecurityContextHolder.clearContext();
        assertNull(userUtils.getCurrentUser());
        assertFalse(userUtils.isCurrentUser(1L));
    }

    @Test
    void testGetCurrentUserNullAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertNull(userUtils.getCurrentUser());
        assertFalse(userUtils.isCurrentUser(1L));
    }
}
