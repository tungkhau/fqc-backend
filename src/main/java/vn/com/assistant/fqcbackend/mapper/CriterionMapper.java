package vn.com.assistant.fqcbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.assistant.fqcbackend.dto.CriterionRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriterionResponseDTO;
import vn.com.assistant.fqcbackend.dto.GradeRequestDTO;
import vn.com.assistant.fqcbackend.dto.GradeResponseDTO;
import vn.com.assistant.fqcbackend.entity.Criterion;
import vn.com.assistant.fqcbackend.entity.Grade;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CriterionMapper extends MapStructMapper {
    CriterionMapper INSTANCE = Mappers.getMapper(CriterionMapper.class);
    Grade gradeRequestDTOtoGrade(GradeRequestDTO gradeRequestDTO);
    @Mapping(source = "no", target = "no")
    GradeResponseDTO gradeToGradeResponseDTO(Grade grade);
    List<Grade> listGradeRequestDTOToListGrade(List<GradeRequestDTO> gradeRequestDTOList);
    Criterion criterionRequestDTOtoCriterion(CriterionRequestDTO criterionRequestDTO);
    CriterionResponseDTO criterionToCriterionResponseDTO(Criterion criterion);
    List<CriterionResponseDTO> listCriterionToListCriterionResponseDTO(List<Criterion> criterionList);
    default Criterion mapGrades(List<GradeRequestDTO> gradeRequestDTOList) {
        Criterion criterion = new Criterion();
        criterion.setGradeList(listGradeRequestDTOToListGrade(gradeRequestDTOList));
        return criterion;
    }

}
