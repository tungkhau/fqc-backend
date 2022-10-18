package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LotResponseDTO {
    private String id;
    private String code;
    private Integer expectedQuantity;
    private Integer expectedWeight;
    private String orderCode;
    private String fabricCode;
    private String fabricName;
    private String colorCode;
    private String colorName;
    private String customerName;
    private MeasurementResponseDTO measurement;
}
