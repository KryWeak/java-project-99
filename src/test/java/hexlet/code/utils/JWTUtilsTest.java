package hexlet.code.utils;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class JWTUtilsTest {

    @Test
    void testGenerateToken() {
        JwtEncoder encoder = Mockito.mock(JwtEncoder.class);

        Jwt jwt = new Jwt(
                "mocked-token",
                Instant.now(),
                Instant.now().plusSeconds(3600),
                Map.of("alg", "none"),
                Map.of("sub", "user@test.com")
        );

        Mockito.when(encoder.encode(any(JwtEncoderParameters.class)))
                .thenReturn(jwt);

        JWTUtils utils = new JWTUtils();
        utils.encoder = encoder;

        String token = utils.generateToken("user@test.com");

        assertEquals("mocked-token", token);
    }
}
