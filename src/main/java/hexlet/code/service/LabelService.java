package hexlet.code.service;

import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public void createLabel(String name) {

        var taskStatus = new Label();
        taskStatus.setName(name);
        labelRepository.save(taskStatus);
    }
}
