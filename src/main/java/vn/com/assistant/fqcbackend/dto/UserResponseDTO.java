package vn.com.assistant.fqcbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;
    private String role;
}
