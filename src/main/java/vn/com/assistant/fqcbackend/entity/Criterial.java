package vn.com.assistant.fqcbackend.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import vn.com.assistant.fqcbackend.entity.enums.Unit;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "criterials", uniqueConstraints = {@UniqueConstraint(name = "name", columnNames = "name")})
public class Criterial {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
