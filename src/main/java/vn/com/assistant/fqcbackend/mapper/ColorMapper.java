package vn.com.assistant.fqcbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.assistant.fqcbackend.dto.ColorRequestDTO;
import vn.com.assistant.fqcbackend.dto.ColorResponseDTO;
import vn.com.assistant.fqcbackend.entity.Color;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ColorMapper extends MapStructMapper {
    ColorMapper INSTANCE = Mappers.getMapper(ColorMapper.class);

    @Mapping(target = "customerName", source = "customer.name")
    @Mapping(target = "customerId", source = "customer.id")
    ColorResponseDTO colorToColorResponseDTO(Color color);

    List<ColorResponseDTO> listColorToListColorResponseDTO(List<Color> colorList);

    Color colorRequestDTOtoColor(ColorRequestDTO colorRequestDTO);
}
