package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ColorRequestDTO {
    @Size(min = 1, max = 16, message = "{color.code.size}")
    String code;
    @Size(min = 1, max = 16, message = "{color.name.size}")
    @NotBlank(message = "{color.name.notBlank}")
    String name;
    @NotBlank(message = "{customer.id.notBlank}")
    String customerId;
}
