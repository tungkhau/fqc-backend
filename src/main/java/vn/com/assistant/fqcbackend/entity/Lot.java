package vn.com.assistant.fqcbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Table(name = "lots", uniqueConstraints = {@UniqueConstraint(name = "code", columnNames = "code")})
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lot {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "code")
    private String code;

    @Column(name = "expected_quantity")
    private Integer expectedQuantity;

    @Column(name = "expected_weight")
    private Integer expectedWeight;

    @Column(name = "order_number")
    private String orderNumber;

    @OneToMany
    @JoinColumn(name = "lot_id")
    private List<Inspecting> inspectings;

    @OneToOne
    @JoinColumn(name = "lot_id")
    private Measurement measurement;
}
