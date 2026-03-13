package hexlet.code.service;

import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.dto.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository repository;
    private final TaskStatusMapper mapper;

    public TaskStatusServiceImpl(
            TaskStatusRepository repository,
            TaskStatusMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<TaskStatusDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public TaskStatusDTO getById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("TaskStatus with id " + id + " not found"));

        return mapper.map(entity);
    }

    @Override
    public TaskStatusDTO create(TaskStatusCreateDTO data) {
        var entity = mapper.map(data);
        repository.save(entity);
        return mapper.map(entity);
    }

    @Override
    public TaskStatusDTO update(Long id, TaskStatusUpdateDTO data) {
        var entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("TaskStatus with id " + id + " not found"));

        mapper.update(data, entity);
        repository.save(entity);

        return mapper.map(entity);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("TaskStatus with id " + id + " not found"));

        repository.deleteById(id);
    }
}
