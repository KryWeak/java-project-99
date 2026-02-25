package hexlet.code.controller;

import hexlet.code.dto.LabelCreateDTO;
import hexlet.code.dto.LabelDTO;
import hexlet.code.dto.LabelUpdateDTO;
import hexlet.code.service.LabelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/labels")
    public ResponseEntity<List<LabelDTO>> indexLabels() {
        var result = labelService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @GetMapping("/labels/{id}")
    public LabelDTO showLabel(@PathVariable Long id) {
        return labelService.getById(id);
    }

    @PostMapping("/labels")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO createLabel(@RequestBody @Valid LabelCreateDTO data) {
        return labelService.create(data);
    }

    @PutMapping("/labels/{id}")
    public LabelDTO updateLabel(@PathVariable Long id,
                                @Valid @RequestBody LabelUpdateDTO data) {
        return labelService.update(id, data);
    }

    @DeleteMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Long id) {
        labelService.delete(id);
    }
}
