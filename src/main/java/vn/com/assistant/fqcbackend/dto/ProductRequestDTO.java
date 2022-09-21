package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.assistant.fqcbackend.entity.enums.Label;

@Setter
@Getter
public class ProductRequestDTO {
    String fabricId;
    String colorId;
    String criteriaId;
    Label label;
}
