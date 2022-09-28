package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequestDTO {
    String fabricId;
    String colorId;
    String criterionId;
    String label;
}
