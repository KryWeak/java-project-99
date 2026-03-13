package hexlet.code.controller;

import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.dto.TaskStatusUpdateDTO;
import hexlet.code.service.TaskStatusService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskStatusController {

    private final TaskStatusService taskStatusService;

    public TaskStatusController(TaskStatusService taskStatusService) {
        this.taskStatusService = taskStatusService;
    }

    @GetMapping(path = "/task_statuses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskStatusDTO>> indexTaskStatuses() {

        var result = taskStatusService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @GetMapping(path = "/task_statuses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO showTaskStatus(@PathVariable Long id) {
        return taskStatusService.getById(id);
    }

    @PostMapping(path = "/task_statuses")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatusDTO createTaskStatus(@RequestBody @Valid TaskStatusCreateDTO data) {
        return taskStatusService.create(data);
    }

    @PutMapping(path = "/task_statuses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO updateTaskStatus(
            @Valid @RequestBody TaskStatusUpdateDTO data,
            @PathVariable Long id
    ) {
        return taskStatusService.update(id, data);
    }

    @DeleteMapping("/task_statuses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskStatus(@PathVariable Long id) {
        taskStatusService.delete(id);
    }
}
