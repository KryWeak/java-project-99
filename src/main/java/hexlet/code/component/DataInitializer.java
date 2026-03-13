package hexlet.code.component;

import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.CustomUserDetailService;
import hexlet.code.service.LabelService;
import hexlet.code.service.TaskStatusService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import hexlet.code.dto.TaskStatusCreateDTO;

@Component
public class DataInitializer implements ApplicationRunner {

    private final CustomUserDetailService userService;
    private final TaskStatusService taskStatusService;
    private final LabelService labelService;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final LabelRepository labelRepository;

    public DataInitializer(
            CustomUserDetailService userService,
            TaskStatusService taskStatusService,
            LabelService labelService,
            UserRepository userRepository,
            TaskStatusRepository taskStatusRepository,
            LabelRepository labelRepository) {

        this.userService = userService;
        this.taskStatusService = taskStatusService;
        this.labelService = labelService;
        this.userRepository = userRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.labelRepository = labelRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        if (userRepository.findAll().isEmpty()) {
            var userData = new User();
            userData.setFirstName("hexlet");
            userData.setEmail("hexlet@example.com");
            userData.setPassword("qwerty");
            userService.createUser(userData);
        }

        if (taskStatusRepository.findAll().isEmpty()) {

            var draft = new TaskStatusCreateDTO();
            draft.setName("Draft");
            draft.setSlug("draft");
            taskStatusService.create(draft);

            var review = new TaskStatusCreateDTO();
            review.setName("ToReview");
            review.setSlug("to_review");
            taskStatusService.create(review);

            var fixed = new TaskStatusCreateDTO();
            fixed.setName("ToBeFixed");
            fixed.setSlug("to_be_fixed");
            taskStatusService.create(fixed);

            var publish = new TaskStatusCreateDTO();
            publish.setName("ToPublish");
            publish.setSlug("to_publish");
            taskStatusService.create(publish);

            var published = new TaskStatusCreateDTO();
            published.setName("Published");
            published.setSlug("published");
            taskStatusService.create(published);
        }

        if (labelRepository.findAll().isEmpty()) {
            labelService.createLabel("feature");
            labelService.createLabel("bug");
        }
    }
}
