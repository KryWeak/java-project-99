package hexlet.code.service;

import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import org.junit.jupiter.api.Test;
import hexlet.code.mapper.LabelMapper;
import org.mapstruct.factory.Mappers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LabelServiceTest {

    @Test
    void testCreateLabel() {
        LabelRepository repo = mock(LabelRepository.class);
        LabelMapper mapper = Mappers.getMapper(LabelMapper.class);

        LabelService service = new LabelServiceImpl(repo, mapper);

        service.createLabel("Urgent");

        verify(repo).save(any(Label.class));
    }
}
