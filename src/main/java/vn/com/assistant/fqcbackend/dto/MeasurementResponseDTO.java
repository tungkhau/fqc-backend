package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class MeasurementResponseDTO {
    private String id;
    private Float totalWidth;
    private Float usableWidth;
    private Float areaDensity;
}
