package vn.com.assistant.fqcbackend.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customers", uniqueConstraints = {@UniqueConstraint(name = "code", columnNames = "code"),
        @UniqueConstraint(name = "name", columnNames = "name"), @UniqueConstraint(name = "taxCode", columnNames = "tax_code")})
public class Customer {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany
    @JoinColumn(name = "customer_id")
    private List<Color> colorList;

    @OneToMany
    @JoinColumn(name = "customer_id")
    private List<Fabric> fabricList;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false)
    private Date createdTime;

}
