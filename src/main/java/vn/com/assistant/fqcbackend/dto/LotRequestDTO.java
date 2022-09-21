package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LotRequestDTO {
    private String code;
    @NotNull
    @Min(value=0, message="{expectedQuantity.inValid}")
    private Integer expectedQuantity;
    @NotNull
    @Min(value=0, message="{expectedWeight.inValid}")
    private Integer expectedWeight;
    @NotNull
    @Min(value=0, message="{orderNumber.inValid}")
    private Integer orderNumber;
}
