package hexlet.code.service;

import hexlet.code.dto.LabelCreateDTO;
import hexlet.code.dto.LabelDTO;
import hexlet.code.dto.LabelUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    public LabelServiceImpl(LabelRepository labelRepository,
                            LabelMapper labelMapper) {
        this.labelRepository = labelRepository;
        this.labelMapper = labelMapper;
    }

    @Override
    public List<LabelDTO> getAll() {
        return labelRepository.findAll()
                .stream()
                .map(labelMapper::map)
                .toList();
    }

    @Override
    public LabelDTO getById(Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Label with id " + id + " not found"));

        return labelMapper.map(label);
    }

    @Override
    public LabelDTO create(LabelCreateDTO dto) {
        var label = labelMapper.map(dto);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    @Override
    public LabelDTO update(Long id, LabelUpdateDTO dto) {
        var label = labelRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Label with id " + id + " not found"));

        labelMapper.update(dto, label);
        labelRepository.save(label);

        return labelMapper.map(label);
    }

    @Override
    public void delete(Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Label with id " + id + " not found"));

        labelRepository.delete(label);
    }

    @Override
    public void createLabel(String name) {
        var label = new Label();
        label.setName(name);
        labelRepository.save(label);
    }
}
