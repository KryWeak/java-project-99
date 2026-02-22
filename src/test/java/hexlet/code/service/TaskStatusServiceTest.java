package hexlet.code.service;

import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TaskStatusServiceTest {

    @Test
    void testCreateTaskStatus() {
        TaskStatusRepository repo = mock(TaskStatusRepository.class);
        TaskStatusService service = new TaskStatusService();
        service.taskStatusRepository = repo;

        service.createTaskStatus("Open", "open");
        verify(repo).save(any(TaskStatus.class));
    }
}
