package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriterialResponseDTO {
    private String id;
    private String name;
    private String unit;
    private UserResponseDTO user;

}
