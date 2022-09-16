package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {
    private String code;
    private String password;
}
