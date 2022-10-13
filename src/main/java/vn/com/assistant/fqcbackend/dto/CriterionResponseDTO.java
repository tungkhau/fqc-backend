package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CriterionResponseDTO {
    private String id;
    private String name;
    private String unit;
    private List<GradeResponseDTO> gradeResponseDTOList;
}
