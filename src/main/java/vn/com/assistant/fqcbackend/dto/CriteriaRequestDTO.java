package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class CriteriaRequestDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String unit;
    @Size(min = 1)
    private List<GradeRequestDTO> grades;
}
