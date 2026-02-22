package hexlet.code.service;

import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class CustomUserDetailServiceTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private CustomUserDetailService service;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        passwordEncoder = mock(PasswordEncoder.class);
        service = new CustomUserDetailService();
        service.userRepository = userRepository;
        service.userMapper = userMapper;
        service.passwordEncoder = passwordEncoder;
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        UserDetails loaded = service.loadUserByUsername("test@example.com");
        assertEquals(user, loaded);
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("missing@example.com"));
    }

    @Test
    void testCreateUserWithUserDetails() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userDetails.getPassword()).thenReturn("secret");
        when(passwordEncoder.encode("secret")).thenReturn("encoded");

        service.createUser(userDetails);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUnsupportedMethods() {
        assertThrows(UnsupportedOperationException.class, () -> service.updateUser(mock(UserDetails.class)));
        assertThrows(UnsupportedOperationException.class, () -> service.deleteUser("username"));
        assertThrows(UnsupportedOperationException.class, () -> service.changePassword("old", "new"));
        assertThrows(UnsupportedOperationException.class, () -> service.userExists("username"));
    }
}
