package vn.com.assistant.fqcbackend.utility;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.assistant.fqcbackend.dto.CriterialRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriterialResponseDTO;
import vn.com.assistant.fqcbackend.dto.GradeRequestDTO;
import vn.com.assistant.fqcbackend.dto.GradeResponseDTO;
import vn.com.assistant.fqcbackend.entity.Criterial;
import vn.com.assistant.fqcbackend.entity.Grade;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CriterialMapper extends MapStructMapper{
    CriterialMapper INSTANCE = Mappers.getMapper(CriterialMapper.class);
    Grade gradeRequestDTOtoGrade(GradeRequestDTO gradeRequestDTO);
    @Mapping(source = "no", target = "no")
    GradeResponseDTO gradeToGradeResponseDTO(Grade grade);
    List<Grade> listGradeRequestDTOToListGrade(List<GradeRequestDTO> gradeRequestDTOList);
    List<GradeResponseDTO> listGradeToListGradeResponseDTO(List<Grade> gradeList);
    Criterial criterialRequestDTOtoCriterial (CriterialRequestDTO criterialRequestDTO);
    CriterialResponseDTO criterialToCriterialResponseDTO (Criterial criterial);
    List<CriterialResponseDTO> listCriterialToListCriterialResponseDTO (List<Criterial> criterialList);
    default Criterial mapGrades(List<GradeRequestDTO> gradeRequestDTOList) {
        Criterial criterial = new Criterial();
        criterial.setGrades(listGradeRequestDTOToListGrade(gradeRequestDTOList));
        return criterial;
    }

}
