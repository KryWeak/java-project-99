package hexlet.code.service;

import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskParamsDTO;
import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.specification.TaskSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskSpecification taskSpecification;

    public TaskServiceImpl(TaskRepository taskRepository,
                           TaskMapper taskMapper,
                           TaskSpecification taskSpecification) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskSpecification = taskSpecification;
    }

    @Override
    public List<TaskDTO> getAll(TaskParamsDTO params, int page) {
        var specification = taskSpecification.build(params);
        var tasks = taskRepository.findAll(specification, PageRequest.of(page - 1, 10));
        return tasks.map(taskMapper::map).getContent();
    }

    @Override
    public TaskDTO getById(Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        return taskMapper.map(task);
    }

    @Override
    public TaskDTO create(TaskCreateDTO data) {
        var task = taskMapper.map(data);
        taskRepository.save(task);

        TaskStatus taskStatus = task.getTaskStatus();
        if (taskStatus != null) {
            taskStatus.addTask(task);
        }

        User userTask = task.getAssignee();
        if (userTask != null) {
            userTask.addTask(task);
        }

        return taskMapper.map(task);
    }

    @Override
    public TaskDTO update(Long id, TaskUpdateDTO data) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        taskMapper.update(data, task);
        taskRepository.save(task);

        return taskMapper.map(task);
    }

    @Override
    public void delete(Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        taskRepository.deleteById(id);
    }
}
