package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LotRequestDTO {
    @Size(min = 1, max = 16, message = "{lot.code.size}")
    private String code;
    @Min(value = 0, message = "{expectedQuantity.inValid}")
    private Integer expectedQuantity;
    @Min(value = 0, message = "{expectedWeight.inValid}")
    private Integer expectedWeight;
    @Size(min = 1, max = 16, message = "{lot.orderCode.size}")
    private String orderCode;
    @NotBlank(message = "{product.id.notBlank}")
    private String productId;
}
