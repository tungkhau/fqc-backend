package vn.com.assistant.fqcbackend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import vn.com.assistant.fqcbackend.entity.enums.Label;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "fabric_id", nullable = false, updatable = false)
    private Fabric fabric;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false, updatable = false)
    private Color color;

    @Column(name = "label", insertable = false)
    @Enumerated(EnumType.STRING)
    private Label label;

    @ManyToOne
    @JoinColumn(name = "criterion_id", insertable = false)
    private Criterion criterion;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<Lot> lotList;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false)
    private Date createdTime;
}
