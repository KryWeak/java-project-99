package hexlet.code.mapper;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        uses = { JsonNullableMapper.class },
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    @Autowired
    protected TaskStatusRepository taskStatusRepository;

    @Autowired
    protected LabelRepository labelRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JsonNullableMapper jsonNullableMapper;

    @Mapping(source = "title", target = "name")
    @Mapping(source = "content", target = "description")
    @Mapping(target = "taskStatus", expression = "java(defineStatusFromCreateDTO(dto))")
    @Mapping(source = "assigneeId", target = "assignee", qualifiedByName = "mapAssignee")
    @Mapping(target = "labels", expression = "java(defineListLabelFromCreateDTO(dto))")
    public abstract Task map(TaskCreateDTO dto);

    public void update(TaskUpdateDTO dto, @MappingTarget Task model) {
        if (dto.getTitle() != null && dto.getTitle().isPresent()) {
            model.setName(dto.getTitle().get());
        }

        if (dto.getContent() != null && dto.getContent().isPresent()) {
            model.setDescription(dto.getContent().get());
        }

        if (dto.getIndex() != null && dto.getIndex().isPresent()) {
            model.setIndex(dto.getIndex().get());
        }

        if (dto.getAssigneeId() != null && dto.getAssigneeId().isPresent()) {
            model.setAssignee(mapAssignee(dto.getAssigneeId().get()));
        }

        if (dto.getTaskLabelIds() != null && dto.getTaskLabelIds().isPresent()) {
            List<Long> labelIds = dto.getTaskLabelIds().get();
            model.setLabels(labelIds == null ? new ArrayList<>() : defineListLabel(labelIds));
        }

        if (dto.getStatus() != null && dto.getStatus().isPresent()) {
            String status = dto.getStatus().get();
            model.setTaskStatus(status == null ? null : defineStatus(status));
        }
    }

    @Mapping(source = "name", target = "title")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "assignee.id", target = "assigneeId")
    @Mapping(target = "taskLabelIds", expression = "java(defineListIds(model.getLabels()))")
    public abstract TaskDTO map(Task model);

    @Named("mapAssignee")
    protected User mapAssignee(Long id) {
        if (id == null) {
            return null;
        }

        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found")
        );
    }

    public List<Label> defineListLabel(List<Long> labelIds) {
        if (labelIds == null || labelIds.isEmpty()) {
            return new ArrayList<>();
        }

        return labelRepository.findAllById(labelIds);
    }

    public List<Label> defineListLabelFromCreateDTO(TaskCreateDTO data) {
        if (data == null || data.getTaskLabelIds() == null) {
            return new ArrayList<>();
        }
        return defineListLabel(data.getTaskLabelIds());
    }

    public TaskStatus defineStatusFromCreateDTO(TaskCreateDTO data) {
        if (data == null || data.getStatus() == null) {
            return null;
        }
        return defineStatus(data.getStatus());
    }

    public TaskStatus defineStatus(String status) {
        return status == null || status.isEmpty() ? null : taskStatusRepository.findBySlug(status).orElse(null);
    }

    public List<Long> defineListIds(List<Label> labels) {
        if (labels == null) {
            return new ArrayList<>();
        }
        List<Long> result = new ArrayList<>();
        for (Label label : labels) {
            if (label != null) {
                result.add(label.getId());
            }
        }
        return result;
    }
}
