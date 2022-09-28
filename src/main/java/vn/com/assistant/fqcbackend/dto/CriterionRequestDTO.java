package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CriterionRequestDTO {
    private String name;
    private String unit;
    private List<GradeRequestDTO> grades;
}