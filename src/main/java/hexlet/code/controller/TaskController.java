package hexlet.code.controller;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskParamsDTO;
import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> index(TaskParamsDTO params,
                                               @RequestParam(defaultValue = "1") int page) {

        var tasks = taskService.getAll(params, page);

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(tasks.size()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(tasks);
    }

    @GetMapping("/tasks/{id}")
    public TaskDTO showTask(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@RequestBody @Valid TaskCreateDTO data) {
        return taskService.create(data);
    }

    @PutMapping("/tasks/{id}")
    public TaskDTO updateTask(@Valid @RequestBody TaskUpdateDTO data,
                              @PathVariable Long id) {
        return taskService.update(id, data);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.delete(id);
    }
}
