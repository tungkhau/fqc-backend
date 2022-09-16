package vn.com.assistant.fqcbackend.utility;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import vn.com.assistant.fqcbackend.dto.StaffRequestDTO;
import vn.com.assistant.fqcbackend.dto.StaffResponseDTO;
import vn.com.assistant.fqcbackend.entity.User;


@Mapper(componentModel = "spring")
public interface StaffMapper extends MapStructMapper {
    StaffMapper INSTANCE = Mappers.getMapper(StaffMapper.class);

    StaffResponseDTO userToStaffResponseDTO(User user);

    User staffRequestDTOtoUser(StaffRequestDTO staffRequestDTO);


    @Mapping(target = "code", ignore = true)
    void updateUserFromStaffRequestDTO(StaffRequestDTO staffRequestDTO, @MappingTarget User user);
}
