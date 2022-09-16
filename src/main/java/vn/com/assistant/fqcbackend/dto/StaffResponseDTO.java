package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.assistant.fqcbackend.entity.Role;

@Setter
@Getter
public class StaffResponseDTO {
    String id;
    String code;
    String name;
    Role role;
    boolean active;
}
