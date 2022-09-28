package vn.com.assistant.fqcbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
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

    @Column(name = "code", updatable = false)
    private String code;

    @Column(name = "expected_quantity", nullable = false)
    private Integer expectedQuantity;

    @Column(name = "expected_weight", nullable = false)
    private Integer expectedWeight;

    @Column(name = "order_code")
    private String orderCode;

    @ManyToOne
    private Product product;

    @OneToMany(mappedBy = "lot")
    private List<InspectingSession> inspectingSessionList;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "lot")
    private Measurement measurement;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false)
    private Date createdTime;
}
