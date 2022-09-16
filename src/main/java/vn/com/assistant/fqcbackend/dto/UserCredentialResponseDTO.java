package vn.com.assistant.fqcbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialResponseDTO {
    private Long id;
    private String code;
    private String token;
    private String role;
}
