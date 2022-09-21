package vn.com.assistant.fqcbackend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.CriterialRequestDTO;
import vn.com.assistant.fqcbackend.entity.Criterial;
import vn.com.assistant.fqcbackend.entity.enums.Unit;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.CriterialRepository;
import vn.com.assistant.fqcbackend.service.CriterialServiceImp;
import vn.com.assistant.fqcbackend.service.CustomerServiceImp;
import vn.com.assistant.fqcbackend.utility.CriterialMapper;

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
public class CriterialServiceTests {
    @Mock
    CriterialRepository criterialRepository;
    @Mock
    Environment env;
    @Captor
    private ArgumentCaptor<Criterial> criterialArgumentCaptor;
    @InjectMocks
    CriterialServiceImp criterialService;

    @Test
    void canFetch() {
        criterialService.fetch();
        verify(criterialRepository).findAll();
    }

    @Test
    void canCreate() {
        //given
        CriterialRequestDTO requestDTO = genMockCriterialRequest();
        criterialArgumentCaptor = ArgumentCaptor.forClass(Criterial.class);
        Criterial criterial = CriterialMapper.INSTANCE.criterialRequestDTOtoCriterial(requestDTO);

        //when
        criterialService.create(requestDTO);
        //then
        Mockito.verify(criterialRepository).save(criterialArgumentCaptor.capture());
        Criterial capturedCriterial = criterialArgumentCaptor.getValue();

        assertThat(capturedCriterial)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(criterial);
    }

    @Test
    void canDelete(){
        //give
        String criterialId = UUID.randomUUID().toString();
        Criterial criterial = genMockCriterial();
        criterial.setId(criterialId);
        given(criterialRepository.findById(criterialId)).willReturn(Optional.of(criterial));

        //when
        criterialService.delete(criterialId);
        //then
        Mockito.verify(criterialRepository).deleteById(criterialId);
    }
    @Test
    void deleteFailedNotFound(){
        //give
        String criterialId = UUID.randomUUID().toString();
        Criterial criterial = genMockCriterial();
        criterial.setId(criterialId);
        given(criterialRepository.findById(criterialId)).willReturn(Optional.empty());

        when(env.getProperty("criterial.notExisted")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> criterialService.delete(criterialId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

        verify(criterialRepository, never()).delete(any());
    }

    private CriterialRequestDTO genMockCriterialRequest(){
        CriterialRequestDTO requestDTO = new CriterialRequestDTO();
        requestDTO.setName("Name request");
        requestDTO.setUnit(Unit.METER.name());
        return requestDTO;
    }

    private Criterial genMockCriterial(){
        Criterial criterial = new Criterial();
        criterial.setId(UUID.randomUUID().toString());
        criterial.setUnit(Unit.SQUAREMETER);
        criterial.setName("Name");
        criterial.setLots(new ArrayList<>());
        criterial.setGrades(new ArrayList<>());
        return criterial;
    }
}