package hexlet.code.service;

import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskStatusServiceTest {

    @Test
    void testCreateTaskStatus() {

        TaskStatusRepository repo = mock(TaskStatusRepository.class);
        TaskStatusMapper mapper = mock(TaskStatusMapper.class);

        TaskStatusService service = new TaskStatusServiceImpl(repo, mapper);

        TaskStatusCreateDTO dto = new TaskStatusCreateDTO();
        dto.setName("Open");
        dto.setSlug("open");

        when(mapper.map(any(TaskStatusCreateDTO.class))).thenReturn(new TaskStatus());

        service.create(dto);

        verify(repo).save(any(TaskStatus.class));
    }
}
