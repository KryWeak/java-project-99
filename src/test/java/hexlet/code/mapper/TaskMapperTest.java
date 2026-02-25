package hexlet.code.mapper;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskMapperTest {

    private TaskMapper mapper;
    private TaskStatusRepository statusRepo;
    private LabelRepository labelRepo;
    private UserRepository userRepo;

    @BeforeEach
    void setUp() {
        statusRepo = Mockito.mock(TaskStatusRepository.class);
        labelRepo = Mockito.mock(LabelRepository.class);
        userRepo = Mockito.mock(UserRepository.class);

        mapper = Mappers.getMapper(TaskMapper.class);

        mapper.taskStatusRepository = statusRepo;
        mapper.labelRepository = labelRepo;
        mapper.userRepository = userRepo;
    }

    @Test
    void testMapCreateDTOToTask() {
        TaskCreateDTO dto = new TaskCreateDTO();
        dto.setTitle("Task 1");
        dto.setContent("Content 1");
        dto.setAssigneeId(1L);
        dto.setStatus("draft");
        dto.setTaskLabelIds(List.of(1L, 2L));

        TaskStatus status = new TaskStatus();
        status.setSlug("draft");
        when(statusRepo.findBySlug("draft")).thenReturn(Optional.of(status));

        Label l1 = new Label(); l1.setId(1L);
        Label l2 = new Label(); l2.setId(2L);
        when(labelRepo.findAllById(List.of(1L, 2L))).thenReturn(List.of(l1, l2));

        User user = new User(); user.setId(1L);
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        Task task = mapper.map(dto);
        assertEquals("Task 1", task.getName());
        assertEquals("Content 1", task.getDescription());
        assertEquals(user, task.getAssignee());
        assertEquals(status, task.getTaskStatus());
        assertEquals(2, task.getLabels().size());
    }

    @Test
    void testUpdateTask() {
        Task task = new Task();
        task.setName("Old Name");
        task.setDescription("Old Content");
        TaskUpdateDTO dto = new TaskUpdateDTO();
        dto.setTitle(org.openapitools.jackson.nullable.JsonNullable.of("New Name"));
        dto.setContent(org.openapitools.jackson.nullable.JsonNullable.of("New Content"));
        dto.setIndex(org.openapitools.jackson.nullable.JsonNullable.of(10L));

        mapper.update(dto, task);

        assertEquals("New Name", task.getName());
        assertEquals("New Content", task.getDescription());
        assertEquals(10L, task.getIndex());
    }

    @Test
    void testMapTaskToDTO() {
        Task task = new Task();
        task.setName("Task 1");
        TaskDTO dto = mapper.map(task);
        assertEquals("Task 1", dto.getTitle());
    }

    @Test
    void testMapAssigneeNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> mapper.mapAssignee(1L));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testDefineStatusNullOrEmpty() {
        assertNull(mapper.defineStatus(null));
        assertNull(mapper.defineStatus(""));
    }
}
