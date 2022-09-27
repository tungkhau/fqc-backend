package vn.com.assistant.fqcbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.assistant.fqcbackend.dto.FabricRequestDTO;
import vn.com.assistant.fqcbackend.dto.FabricResponseDTO;
import vn.com.assistant.fqcbackend.entity.Fabric;

import java.util.List;


@Mapper(componentModel = "spring")
public interface FabricMapper extends MapStructMapper {
    FabricMapper INSTANCE = Mappers.getMapper(FabricMapper.class);

    @Mapping(target = "customerName", source = "customer.name")
    @Mapping(target = "customerId", source = "customer.id")
    FabricResponseDTO fabricToFabricResponseDTO(Fabric fabric);

    List<FabricResponseDTO> listFabricToListFabricResponseDTO(List<Fabric> fabricList);

    Fabric fabricRequestDTOtoFabric(FabricRequestDTO fabricRequestDTO);
}
