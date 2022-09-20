package vn.com.assistant.fqcbackend.utility;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.assistant.fqcbackend.dto.CriterialRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriterialResponseDTO;
import vn.com.assistant.fqcbackend.entity.Criterial;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CriterialMapper extends MapStructMapper{
    CriterialMapper INSTANCE = Mappers.getMapper(CriterialMapper.class);

    Criterial criterialRequestDTOtoCriterial (CriterialRequestDTO criterialRequestDTO);

    List<CriterialResponseDTO> listCriterialToCriterialResponseDTO (List<Criterial> criterialList);
}
