package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.assistant.fqcbackend.entity.enums.Label;

@Setter
@Getter
public class ProductResponseDTO {
    String id;
    String fabricName;
    String fabricCode;

    String colorName;
    String colorCode;

    String criterionName;

    Label label;
}
