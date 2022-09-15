package vn.com.assistant.fqcbackend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
public class UserCredential {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "encrypted_password", nullable = false)
    private String encryptedPassword;

    @Column(name = "role", nullable = false)
    private String role;
}


