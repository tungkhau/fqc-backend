package vn.com.assistant.fqcbackend.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "measurements")
public class Measurement {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "total_width", nullable = false)
    private Float totalWidth;

    @Column(name = "usable_width", nullable = false)
    private Float usableWidth;

    @Column(name = "area_density", nullable = false)
    private Float areaDensity;

    @OneToOne
    private Lot lot;
}
