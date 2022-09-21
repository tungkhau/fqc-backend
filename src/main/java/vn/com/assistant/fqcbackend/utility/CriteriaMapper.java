package vn.com.assistant.fqcbackend.utility;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.assistant.fqcbackend.dto.CriteriaRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriteriaResponseDTO;
import vn.com.assistant.fqcbackend.dto.GradeRequestDTO;
import vn.com.assistant.fqcbackend.dto.GradeResponseDTO;
import vn.com.assistant.fqcbackend.entity.Criteria;
import vn.com.assistant.fqcbackend.entity.Grade;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CriteriaMapper extends MapStructMapper{
    CriteriaMapper INSTANCE = Mappers.getMapper(CriteriaMapper.class);
    Grade gradeRequestDTOtoGrade(GradeRequestDTO gradeRequestDTO);
    @Mapping(source = "no", target = "no")
    GradeResponseDTO gradeToGradeResponseDTO(Grade grade);
    List<Grade> listGradeRequestDTOToListGrade(List<GradeRequestDTO> gradeRequestDTOList);
    Criteria criteriaRequestDTOtoCriteria(CriteriaRequestDTO criteriaRequestDTO);
    CriteriaResponseDTO criteriaToCriteriaResponseDTO (Criteria criteria);
    List<CriteriaResponseDTO> listCriteriaToListCriteriaResponseDTO(List<Criteria> criteriaList);
    default Criteria mapGrades(List<GradeRequestDTO> gradeRequestDTOList) {
        Criteria criteria = new Criteria();
        criteria.setGrades(listGradeRequestDTOToListGrade(gradeRequestDTOList));
        return criteria;
    }

}
