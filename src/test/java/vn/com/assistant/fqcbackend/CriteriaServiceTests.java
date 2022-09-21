package vn.com.assistant.fqcbackend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.CriteriaRequestDTO;
import vn.com.assistant.fqcbackend.entity.Criteria;
import vn.com.assistant.fqcbackend.entity.enums.Unit;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.CriteriaRepository;
import vn.com.assistant.fqcbackend.service.CriteriaServiceImp;
import vn.com.assistant.fqcbackend.service.CustomerServiceImp;
import vn.com.assistant.fqcbackend.utility.CriteriaMapper;

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
public class CriteriaServiceTests {
    @Mock
    CriteriaRepository criteriaRepository;
    @Mock
    Environment env;
    @Captor
    private ArgumentCaptor<Criteria> criteriaArgumentCaptor;
    @InjectMocks
    CriteriaServiceImp criteriaService;

    @Test
    void canFetch() {
        criteriaService.fetch();
        verify(criteriaRepository).findAll();
    }

    @Test
    void canCreate() {
        //given
        CriteriaRequestDTO requestDTO = genMockCriteriaRequest();
        criteriaArgumentCaptor = ArgumentCaptor.forClass(Criteria.class);
        Criteria criteria = CriteriaMapper.INSTANCE.criteriaRequestDTOtoCriteria(requestDTO);

        //when
        criteriaService.create(requestDTO);
        //then
        Mockito.verify(criteriaRepository).save(criteriaArgumentCaptor.capture());
        Criteria capturedCriteria = criteriaArgumentCaptor.getValue();

        assertThat(capturedCriteria)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(criteria);
    }

    @Test
    void canDelete(){
        //give
        String criteriaId = UUID.randomUUID().toString();
        Criteria criteria = genMockCriteria();
        criteria.setId(criteriaId);
        given(criteriaRepository.findById(criteriaId)).willReturn(Optional.of(criteria));

        //when
        criteriaService.delete(criteriaId);
        //then
        Mockito.verify(criteriaRepository).deleteById(criteriaId);
    }
    @Test
    void deleteFailedNotFound(){
        //give
        String criteriaId = UUID.randomUUID().toString();
        Criteria criteria = genMockCriteria();
        criteria.setId(criteriaId);
        given(criteriaRepository.findById(criteriaId)).willReturn(Optional.empty());

        when(env.getProperty("criteria.notExisted")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> criteriaService.delete(criteriaId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

        verify(criteriaRepository, never()).delete(any());
    }

    private CriteriaRequestDTO genMockCriteriaRequest(){
        CriteriaRequestDTO requestDTO = new CriteriaRequestDTO();
        requestDTO.setName("Name request");
        requestDTO.setUnit(Unit.METER.name());
        return requestDTO;
    }

    private Criteria genMockCriteria(){
        Criteria criteria = new Criteria();
        criteria.setId(UUID.randomUUID().toString());
        criteria.setUnit(Unit.SQUAREMETER);
        criteria.setName("Name");
        criteria.setGrades(new ArrayList<>());
        return criteria;
    }
}