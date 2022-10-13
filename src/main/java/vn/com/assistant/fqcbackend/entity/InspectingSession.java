package vn.com.assistant.fqcbackend.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "inspecting_sessions")
@Getter
@Setter
public class InspectingSession {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "code", updatable = false, nullable = false)
    private String code;

    @Column(name = "machine_no", updatable = false, nullable = false)
    private Integer machineNo;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", updatable = false, nullable = false)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time", insertable = false)
    private Date endTime;

    @Column(name = "weight", insertable = false)
    private Float weight;

    @Column(name = "grade", insertable = false)
    private Integer grade;

    @Column(name = "accepted", insertable = false)
    private boolean accepted;

    @Column(name = "no", insertable = false)
    private Integer no;

    @OneToMany(mappedBy = "inspectingSession")
    @ToString.Exclude
    private List<SessionDefect> sessionDefectList;

    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @ManyToOne
    private User user;
}
