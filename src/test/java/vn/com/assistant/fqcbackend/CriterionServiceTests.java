package vn.com.assistant.fqcbackend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.CriterionRequestDTO;
import vn.com.assistant.fqcbackend.entity.Criterion;
import vn.com.assistant.fqcbackend.entity.enums.Unit;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.CriterionRepository;
import vn.com.assistant.fqcbackend.service.imp.CriterionServiceImp;
import vn.com.assistant.fqcbackend.service.imp.CustomerServiceImp;
import vn.com.assistant.fqcbackend.mapper.CriterionMapper;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {CustomerServiceImp.class})
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class CriterionServiceTests {
    @Mock
    CriterionRepository criterionRepository;
    @Mock
    Environment env;
    @Captor
    private ArgumentCaptor<Criterion> criteriaArgumentCaptor;
    @InjectMocks
    CriterionServiceImp criteriaService;

    @Test
    void canFetch() {
        criteriaService.fetch();
        verify(criterionRepository).findAll();
    }

    @Test
    void canCreate() {
        //given
        CriterionRequestDTO requestDTO = genMockCriteriaRequest();
        criteriaArgumentCaptor = ArgumentCaptor.forClass(Criterion.class);
        Criterion criterion = CriterionMapper.INSTANCE.criterionRequestDTOtoCriterion(requestDTO);

        //when
        criteriaService.create(requestDTO);
        //then
        Mockito.verify(criterionRepository).save(criteriaArgumentCaptor.capture());
        Criterion capturedCriterion = criteriaArgumentCaptor.getValue();

        assertThat(capturedCriterion)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(criterion);
    }

    @Test
    void canDelete(){
        //give
        String criteriaId = UUID.randomUUID().toString();
        Criterion criterion = genMockCriteria();
        criterion.setId(criteriaId);
        given(criterionRepository.findById(criteriaId)).willReturn(Optional.of(criterion));

        //when
        criteriaService.delete(criteriaId);
        //then
        Mockito.verify(criterionRepository).deleteById(criteriaId);
    }
    @Test
    void deleteFailedNotFound(){
        //give
        String criteriaId = UUID.randomUUID().toString();
        Criterion criterion = genMockCriteria();
        criterion.setId(criteriaId);
        given(criterionRepository.findById(criteriaId)).willReturn(Optional.empty());

        when(env.getProperty("criteria.notExisted")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> criteriaService.delete(criteriaId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

        verify(criterionRepository, never()).delete(any());
    }

    private CriterionRequestDTO genMockCriteriaRequest(){
        CriterionRequestDTO requestDTO = new CriterionRequestDTO();
        requestDTO.setName("Name request");
        requestDTO.setUnit(Unit.METER.name());
        return requestDTO;
    }

    private Criterion genMockCriteria(){
        Criterion criterion = new Criterion();
        criterion.setId(UUID.randomUUID().toString());
        criterion.setUnit(Unit.SQUARE_METER);
        criterion.setName("Name");
        criterion.setGradeList(new ArrayList<>());
        return criterion;
    }
}