package vn.com.assistant.fqcbackend.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import vn.com.assistant.fqcbackend.entity.enums.Unit;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "criteria", uniqueConstraints = {@UniqueConstraint(name = "name", columnNames = "name")})
public class Criterion {
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "criterion_id")
    @OrderColumn(name = "no")
    @ToString.Exclude
    private List<Grade> gradeList;

    @OneToMany
    @JoinColumn(name = "criterion_id")
    @ToString.Exclude
    private List<Product> productList;
}
