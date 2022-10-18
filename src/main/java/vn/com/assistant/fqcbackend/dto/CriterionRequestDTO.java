package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class CriterionRequestDTO {
    @Size(min = 1, max = 20, message = "{criterion.name.size}")
    private String name;
    @NotBlank(message = "{critetion.unit.notBlank}")
    private String unit;
    private List<GradeRequestDTO> gradeRequestDTOList;
}
