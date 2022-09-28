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
import vn.com.assistant.fqcbackend.dto.GradeRequestDTO;
import vn.com.assistant.fqcbackend.entity.Criterion;
import vn.com.assistant.fqcbackend.entity.Product;
import vn.com.assistant.fqcbackend.entity.enums.Label;
import vn.com.assistant.fqcbackend.entity.enums.Unit;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.CriterionRepository;
import vn.com.assistant.fqcbackend.service.imp.CriterionServiceImp;
import vn.com.assistant.fqcbackend.service.imp.CustomerServiceImp;
import vn.com.assistant.fqcbackend.mapper.CriterionMapper;

import java.util.ArrayList;
import java.util.List;
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
    void testFetch() {
        criteriaService.fetch();
        verify(criterionRepository).findAll();
    }

    @Test
    void testCreateSuccess() {
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
                .isEqualTo(criterion);
    }

    @Test
    void testCreateFailedInvalidGradeList(){
        //given
        CriterionRequestDTO requestDTO = genMockCriteriaRequest();
        requestDTO.setGrades(genMockListGradeRequestFail());

        when(env.getProperty("criterion.create.invalidGradeList")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> criteriaService.create(requestDTO))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

        verify(criterionRepository, never()).delete(any());

    }

    @Test
    void testCreateFailedInvalidUnit(){
        //given
        CriterionRequestDTO requestDTO = genMockCriteriaRequest();
        requestDTO.setUnit("Unit");

        when(env.getProperty("criterion.create.invalidUnit")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> criteriaService.create(requestDTO))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

        verify(criterionRepository, never()).delete(any());
    }

    @Test
    void testDeleteSuccess(){
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
    void testDeleteFailedUsed(){
        //give
        String criteriaId = UUID.randomUUID().toString();
        Criterion criterion = genMockCriteria();

        List<Product> products = new ArrayList<>();
        products.add(genMockProduct());

        criterion.setProductList(products);
        criterion.setId(criteriaId);

        given(criterionRepository.findById(criteriaId)).willReturn(Optional.of(criterion));

        when(env.getProperty("criterion.used")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> criteriaService.delete(criteriaId))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testDeleteFailedNotFound(){
        //give
        String criteriaId = UUID.randomUUID().toString();
        given(criterionRepository.findById(criteriaId)).willReturn(Optional.empty());

        when(env.getProperty("criterion.notFound")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> criteriaService.delete(criteriaId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

    }

    private CriterionRequestDTO genMockCriteriaRequest(){
        CriterionRequestDTO requestDTO = new CriterionRequestDTO();
        requestDTO.setName("Name request");
        requestDTO.setUnit(Unit.METER.name());
        requestDTO.setGrades(genMockListGradeRequest());
        return requestDTO;
    }

    private Criterion genMockCriteria(){
        Criterion criterion = new Criterion();
        criterion.setId(UUID.randomUUID().toString());
        criterion.setUnit(Unit.SQUARE_METER);
        criterion.setName("Name");
        criterion.setGradeList(new ArrayList<>());
        criterion.setProductList(new ArrayList<>());
        return criterion;
    }
    private List<GradeRequestDTO> genMockListGradeRequest(){
        List<GradeRequestDTO> list = new ArrayList<>();
        list.add(new GradeRequestDTO(21));
        list.add(new GradeRequestDTO(40));
        list.add(new GradeRequestDTO(61));
        list.add(new GradeRequestDTO(80));
        return list;
    }

    private List<GradeRequestDTO> genMockListGradeRequestFail(){
        List<GradeRequestDTO> list = new ArrayList<>();
        list.add(new GradeRequestDTO(61));
        list.add(new GradeRequestDTO(80));
        list.add(new GradeRequestDTO(21));
        list.add(new GradeRequestDTO(40));
        return list;
    }

    private Product genMockProduct(){
        return new Product("Test id", Label.FIRST, null, null, null, new ArrayList<>(), null);

    }

}