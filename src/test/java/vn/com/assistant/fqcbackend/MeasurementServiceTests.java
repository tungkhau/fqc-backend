package vn.com.assistant.fqcbackend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.MeasurementRequestDTO;
import vn.com.assistant.fqcbackend.entity.InspectingSession;
import vn.com.assistant.fqcbackend.entity.Lot;
import vn.com.assistant.fqcbackend.entity.Measurement;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.exception.UnexpectedException;
import vn.com.assistant.fqcbackend.mapper.MeasurementMapper;
import vn.com.assistant.fqcbackend.repository.LotRepository;
import vn.com.assistant.fqcbackend.repository.MeasurementRepository;
import vn.com.assistant.fqcbackend.service.imp.MeasurementServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {CriterionServiceTests.class})
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class MeasurementServiceTests {
    @Mock
    MeasurementRepository measurementRepository;
    @Mock
    LotRepository lotRepository;
    @Mock
    Lot lot;
    @Mock
    List<InspectingSession> inspectingSessionList;
    @Mock
    Environment env;
    @Captor
    private ArgumentCaptor<Measurement> measurementArgumentCaptor;
    @InjectMocks
    MeasurementServiceImp measurementService;

    @Test
    void testCreateSuccess(){
        //given
        MeasurementRequestDTO requestDTO = genMockRequest();
        measurementArgumentCaptor = ArgumentCaptor.forClass(Measurement.class);
        Measurement measurement = MeasurementMapper.INSTANCE.measurementRequestDTOtoMeasurement(requestDTO);
        given(lotRepository.findById(requestDTO.getLotId())).willReturn(Optional.of(lot));
        measurement.setLot(lot);

        //when
        measurementService.create(requestDTO);

        //then
        Mockito.verify(measurementRepository).save(measurementArgumentCaptor.capture());
        Measurement capturedMeasurement = measurementArgumentCaptor.getValue();

        assertThat(capturedMeasurement)
                .usingRecursiveComparison()
                .isEqualTo(measurement);
    }

    @Test
    void testCreateFailedInvalidLot(){
        //given
        MeasurementRequestDTO requestDTO = genMockRequest();
        given(lotRepository.findById(requestDTO.getLotId())).willReturn(Optional.empty());

        when(env.getProperty("lot.notExisted")).thenReturn("Msg");

        //when & then
        assertThatThrownBy(() -> measurementService.create(requestDTO))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testDeleteSuccess(){
        //given
        Measurement measurement = genMockMeasurement();
        given(measurementRepository.findById(measurement.getId())).willReturn(Optional.of(measurement));
        given(lotRepository.findById(measurement.getLot().getId())).willReturn(Optional.of(measurement.getLot()));
        //when
        measurementService.delete(measurement.getId());
        //then
        Mockito.verify(measurementRepository).deleteById(measurement.getId());
    }

    @Test
    void testDeleteFailedNotFound(){
        //given
        String measurementId = "Test id";
        given(measurementRepository.findById(measurementId)).willReturn(Optional.empty());

        when(env.getProperty("measurement.notExisted")).thenReturn("msg");

        //when and then
        assertThatThrownBy(() -> measurementService.delete(measurementId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("msg");
    }

    @Test
    void testDeleteFailedInvalidLot(){
        //given
        Measurement measurement = genMockMeasurement();
        given(measurementRepository.findById(measurement.getId())).willReturn(Optional.of(measurement));
        given(lotRepository.findById(measurement.getLot().getId())).willReturn(Optional.empty());

        //when and then
        assertThatThrownBy(() -> measurementService.delete(measurement.getId()))
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Xay ra loi khong xac dinh");
    }

    @Test
    void testDeleteFailedUsed(){
        //given
        Measurement measurement = genMockMeasurement();
        measurement.getLot().setInspectingSessionList(inspectingSessionList);
        given(measurementRepository.findById(measurement.getId())).willReturn(Optional.of(measurement));
        given(lotRepository.findById(measurement.getLot().getId())).willReturn(Optional.of(measurement.getLot()));

        when(env.getProperty("measurement.used")).thenReturn("Msg");

        //when and then
        assertThatThrownBy(() -> measurementService.delete(measurement.getId()))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Msg");
    }

    private MeasurementRequestDTO genMockRequest(){
        MeasurementRequestDTO requestDTO = new MeasurementRequestDTO();
        requestDTO.setAreaDensity(4.5F);
        requestDTO.setLotId("Lot id");
        requestDTO.setTotalWidth(1F);
        requestDTO.setUsableWidth(2F);
        return requestDTO;
    }

    private Measurement genMockMeasurement(){
        Lot lot = new Lot();
        lot.setId("Lot id");
        List<InspectingSession> inspectingSessionList = new ArrayList<>();
        lot.setInspectingSessionList(inspectingSessionList);

        Measurement measurement = new Measurement();
        measurement.setId(UUID.randomUUID().toString());
        measurement.setLot(lot);
        return measurement;
    }
}
