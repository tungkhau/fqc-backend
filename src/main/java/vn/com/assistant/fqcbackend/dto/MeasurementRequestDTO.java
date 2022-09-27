package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MeasurementRequestDTO {
    private Float totalWidth;
    private Float usableWidth;
    private Float areaDensity;
    private String lotId;
}
