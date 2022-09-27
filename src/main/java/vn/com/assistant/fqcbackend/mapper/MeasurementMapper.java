package vn.com.assistant.fqcbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.assistant.fqcbackend.dto.MeasurementRequestDTO;
import vn.com.assistant.fqcbackend.dto.MeasurementResponseDTO;
import vn.com.assistant.fqcbackend.entity.Measurement;

@Mapper(componentModel = "spring")
public interface MeasurementMapper extends MapStructMapper {
    MeasurementMapper INSTANCE = Mappers.getMapper(MeasurementMapper.class);

    MeasurementResponseDTO measurementToMeasurementResponseDTO (Measurement measurement);

    Measurement measurementRequestDTOtoMeasurement(MeasurementRequestDTO measurementRequestDTO);
}
