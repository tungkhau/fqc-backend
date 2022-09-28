package vn.com.assistant.fqcbackend.service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.MeasurementRequestDTO;
import vn.com.assistant.fqcbackend.entity.Lot;
import vn.com.assistant.fqcbackend.entity.Measurement;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.exception.UnexpectedException;
import vn.com.assistant.fqcbackend.mapper.MeasurementMapper;
import vn.com.assistant.fqcbackend.repository.LotRepository;
import vn.com.assistant.fqcbackend.repository.MeasurementRepository;
import vn.com.assistant.fqcbackend.service.MeasurementService;


@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class MeasurementServiceImp implements MeasurementService {

    private final MeasurementRepository measurementRepository;

    private final LotRepository lotRepository;

    private final Environment env;

    @Override
    public void create(MeasurementRequestDTO measurementRequestDTO) {
        Lot lot = lotRepository.findById(measurementRequestDTO.getLotId()).orElseThrow(() -> new InvalidException(env.getProperty("lot.notExisted")));
        Measurement measurement = MeasurementMapper.INSTANCE.measurementRequestDTOtoMeasurement(measurementRequestDTO);
        measurement.setLot(lot);
        measurementRepository.save(measurement);
    }

    @Override
    public void delete(String measurementId) {
        Measurement measurement = measurementRepository.findById(measurementId).orElseThrow(() -> new InvalidException(env.getProperty("measurement.notExisted")));

        Lot lot = lotRepository.findById(measurement.getLot().getId()).orElseThrow(UnexpectedException::new);
        boolean used = lot.getInspectingSessionList().isEmpty();

        if (!used) throw new ConflictException(env.getProperty("measurement.used"));
        measurementRepository.deleteById(measurementId);
    }
}
