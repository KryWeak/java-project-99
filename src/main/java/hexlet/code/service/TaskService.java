package hexlet.code.service;

import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskParamsDTO;
import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.dto.TaskCreateDTO;
import java.util.List;

public interface TaskService {

    List<TaskDTO> getAll(TaskParamsDTO params, int page);

    TaskDTO getById(Long id);

    TaskDTO create(TaskCreateDTO data);

    TaskDTO update(Long id, TaskUpdateDTO data);

    void delete(Long id);
}
