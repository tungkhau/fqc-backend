package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectingSessionRequestDTO {
    private String machineNo;
    private Float weight;
    private Integer grade;
    private boolean accepted;
    private String lotId;
    private String userId;
}
