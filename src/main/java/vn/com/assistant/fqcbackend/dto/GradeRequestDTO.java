package vn.com.assistant.fqcbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GradeRequestDTO {
    private Integer allowedPoint;
}
