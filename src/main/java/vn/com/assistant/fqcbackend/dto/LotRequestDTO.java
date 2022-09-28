package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LotRequestDTO {
    private String code;
    private Integer expectedQuantity;
    private Integer expectedWeight;
    private String orderCode;
    private String productId;
}
