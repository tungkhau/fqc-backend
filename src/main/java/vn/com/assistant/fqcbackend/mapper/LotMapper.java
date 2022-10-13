package vn.com.assistant.fqcbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import vn.com.assistant.fqcbackend.dto.LotRequestDTO;
import vn.com.assistant.fqcbackend.dto.LotResponseDTO;
import vn.com.assistant.fqcbackend.entity.Lot;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LotMapper extends MapStructMapper {
    LotMapper INSTANCE = Mappers.getMapper(LotMapper.class);
    @Mapping(target = "productId", source = "product.id")
    LotResponseDTO lotToLotResponseDTO (Lot lot);
    List<LotResponseDTO> listLotToLotResponseDTO (List<Lot> lots);
    Lot lotRequestDTOtoLot(LotRequestDTO lotRequestDTO);

    void updateLotFromLotRequestDTO(LotRequestDTO lotRequestDTO, @MappingTarget Lot lot);
}
