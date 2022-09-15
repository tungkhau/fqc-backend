package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCredentialRequestDTO {
    private String code;
    private String password;
}
