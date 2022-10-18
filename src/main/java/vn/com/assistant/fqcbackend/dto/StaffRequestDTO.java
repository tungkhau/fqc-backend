package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.assistant.fqcbackend.entity.enums.Role;

import javax.validation.constraints.Size;

@Setter
@Getter
public class StaffRequestDTO {
    @Size(min = 1, max = 6, message = "{staff.code.size}")
    String code;
    @Size(min = 1, max = 25, message = "{staff.name.size}")
    String name;
    Role role;
    boolean active;
}
