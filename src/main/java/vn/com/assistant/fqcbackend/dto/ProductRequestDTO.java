package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class ProductRequestDTO {
    @NotBlank(message = "{fabric.id.notBlank}")
    String fabricId;
    @NotBlank(message = "{color.id.notBlank}")
    String colorId;
    @NotBlank(message = "{criterion.id.notBlank}")
    String criterionId;
    String label;
}
