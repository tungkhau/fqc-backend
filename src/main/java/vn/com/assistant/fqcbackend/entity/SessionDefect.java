package vn.com.assistant.fqcbackend.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "session_defects")
@Getter
@Setter
public class SessionDefect {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "point", updatable = false, nullable = false)
    private Integer point;

    @ManyToOne
    @JoinColumn(name = "inspecting_session_id")
    private InspectingSession inspectingSession;

    @ManyToOne
    private Defect defect;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false)
    private Date createdTime;
}
