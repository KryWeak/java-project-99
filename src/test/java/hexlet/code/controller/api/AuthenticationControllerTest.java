package hexlet.code.controller.api;

import hexlet.code.controller.AuthenticationController;
import hexlet.code.dto.AuthRequest;
import hexlet.code.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class AuthenticationControllerTest {

    @Test
    void testCreateReturnsToken() {
        JWTUtils jwtUtils = Mockito.mock(JWTUtils.class);
        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

        Mockito.when(jwtUtils.generateToken("user@test.com"))
                .thenReturn("token");

        AuthenticationController controller = new AuthenticationController();
        controller.jwtUtils = jwtUtils;
        controller.authenticationManager = authenticationManager;
        controller.passwordEncoder = passwordEncoder;

        AuthRequest request = new AuthRequest();
        request.setUsername("user@test.com");
        request.setPassword("password");

        String result = controller.create(request);

        assertEquals("token", result);
        Mockito.verify(authenticationManager).authenticate(any());
    }
}
