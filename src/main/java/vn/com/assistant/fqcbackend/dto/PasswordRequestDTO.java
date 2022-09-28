package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PasswordRequestDTO {
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String confirmPassword;
}
