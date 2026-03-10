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
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


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
    void testUpdateWithNullsAndEmpty() {
        Task task = new Task();
        TaskUpdateDTO dto = new TaskUpdateDTO();

        dto.setTitle(JsonNullable.undefined());
        dto.setContent(JsonNullable.undefined());
        dto.setIndex(JsonNullable.undefined());
        dto.setAssigneeId(JsonNullable.undefined());
        dto.setStatus(JsonNullable.undefined());
        dto.setTaskLabelIds(JsonNullable.undefined());

        assertDoesNotThrow(() -> mapper.update(dto, task));
    }

    @Test
    void testUpdateWithNullDTOFields() {
        Task task = new Task();
        TaskUpdateDTO dto = new TaskUpdateDTO();

        dto.setTitle(null);
        dto.setContent(null);
        dto.setIndex(null);
        dto.setAssigneeId(null);
        dto.setStatus(null);
        dto.setTaskLabelIds(null);

        assertDoesNotThrow(() -> mapper.update(dto, task));
    }

    @Test
    void testDefineStatusAndListLabelWithNullOrEmpty() {
        assertNull(mapper.defineStatus(null));
        assertNull(mapper.defineStatus(""));

        assertTrue(mapper.defineListLabel(null).isEmpty());
        assertTrue(mapper.defineListLabel(List.of()).isEmpty());

        TaskCreateDTO dto = new TaskCreateDTO();
        dto.setTaskLabelIds(null);
        assertTrue(mapper.defineListLabelFromCreateDTO(dto).isEmpty());

        dto.setTaskLabelIds(List.of());
        assertTrue(mapper.defineListLabelFromCreateDTO(dto).isEmpty());

        dto.setStatus(null);
        assertNull(mapper.defineStatusFromCreateDTO(dto));
    }

    @Test
    void testMapAssigneeWithNullAndMissing() {
        assertNull(mapper.mapAssignee(null));

        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> mapper.mapAssignee(1L));

        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testMapAssigneeFound() {
        User user = new User();
        user.setId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        User result = mapper.mapAssignee(1L);

        assertEquals(user, result);
    }

    @Test
    void testDefineListLabelWithValues() {
        Label label = new Label();
        label.setId(1L);

        when(labelRepo.findAllById(List.of(1L))).thenReturn(List.of(label));

        List<Label> result = mapper.defineListLabel(List.of(1L));

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testDefineStatusFound() {
        TaskStatus status = new TaskStatus();
        status.setSlug("draft");

        when(statusRepo.findBySlug("draft")).thenReturn(Optional.of(status));

        TaskStatus result = mapper.defineStatus("draft");

        assertEquals(status, result);
    }

    @Test
    void testDefineListIdsWithNull() {
        List<Long> result = mapper.defineListIds(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void testMapCreateDTOWithNullLabelsAndStatus() {
        TaskCreateDTO dto = new TaskCreateDTO();
        dto.setTitle("Task");
        dto.setContent("Content");
        dto.setAssigneeId(null);
        dto.setTaskLabelIds(null);
        dto.setStatus(null);

        Task task = mapper.map(dto);

        assertEquals("Task", task.getName());
        assertEquals("Content", task.getDescription());
        assertNull(task.getAssignee());
        assertNull(task.getTaskStatus());
        assertTrue(task.getLabels().isEmpty());
    }

    @Test
    void testMapTaskToDTOWithNullFields() {
        Task task = new Task();
        task.setName("My Task");
        task.setDescription(null);
        task.setTaskStatus(null);
        task.setAssignee(null);
        task.setLabels(null);

        TaskDTO dto = mapper.map(task);

        assertEquals("My Task", dto.getTitle());
        assertNull(dto.getContent());
        assertNull(dto.getStatus());
        assertNull(dto.getAssigneeId());
        assertTrue(dto.getTaskLabelIds().isEmpty());
    }
}
