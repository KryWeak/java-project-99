package hexlet.code.service;

import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LabelServiceTest {

    @Test
    void testCreateLabel() {
        LabelRepository repo = mock(LabelRepository.class);
        LabelService service = new LabelService();
        service.labelRepository = repo;

        service.createLabel("Urgent");
        verify(repo).save(any(Label.class));
    }
}
