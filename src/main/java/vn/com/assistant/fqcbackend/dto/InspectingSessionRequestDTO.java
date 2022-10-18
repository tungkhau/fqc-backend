package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class InspectingSessionRequestDTO {
    @NotBlank(message = "{inspectingSession.machineNo.notBlank}")
    private String machineNo;
    private Float weight;
    private Integer grade;
    private boolean accepted;
    private String lotId;
    private String userId;
}
