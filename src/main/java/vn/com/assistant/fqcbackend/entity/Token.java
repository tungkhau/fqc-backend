package vn.com.assistant.fqcbackend.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    @Column(name = "user_code", nullable = false)
    private String userCode;

    @Column(name = "value", nullable = false)
    private String value;
}
