package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class FabricRequestDTO {
    @Size(min = 1, max = 16, message = "{fabric.code.size}")
    String code;
    @Size(min = 1, max = 50, message = "{fabric.name.size}")
    @NotBlank(message = "{fabric.name.notBlank}")
    String name;
    @NotBlank(message = "{customer.id.notBlank}")
    String customerId;
}
