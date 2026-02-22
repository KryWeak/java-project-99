package hexlet.code.service;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder);
    }

    @Test
    void testGetAllUsers() {
        User user = new User();
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.map(user)).thenReturn(new UserDTO());

        List<UserDTO> users = userService.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void testGetUserByIdSuccess() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.map(user)).thenReturn(new UserDTO());

        UserDTO dto = userService.getUserById(1L);
        assertNotNull(dto);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testCreateUser() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setPassword("secret");

        User user = new User();
        when(userMapper.map(dto)).thenReturn(user);
        when(passwordEncoder.encode("secret")).thenReturn("encoded");
        when(userMapper.map(user)).thenReturn(new UserDTO());

        UserDTO created = userService.createUser(dto);
        assertNotNull(created);
        verify(userRepository).save(user);
        assertEquals("encoded", user.getPassword());
    }

    @Test
    void testUpdateUserSuccess() {
        UserUpdateDTO dto = new UserUpdateDTO();
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userMapper).update(dto, user);
        when(userMapper.map(user)).thenReturn(new UserDTO());

        UserDTO updated = userService.updateUser(1L, dto);
        assertNotNull(updated);
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUserNotFound() {
        UserUpdateDTO dto = new UserUpdateDTO();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, dto));
    }

    @Test
    void testDeleteUserSuccess() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);
        verify(userRepository).delete(user);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }
}