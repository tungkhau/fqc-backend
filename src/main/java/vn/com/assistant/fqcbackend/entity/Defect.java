package vn.com.assistant.fqcbackend.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import vn.com.assistant.fqcbackend.entity.enums.Stage;
import vn.com.assistant.fqcbackend.entity.enums.Unit;

import javax.persistence.*;

@Entity
@Table(name = "defects")
@Getter
@Setter
public class Defect {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "stage", nullable = false)
    @Enumerated(EnumType.STRING)
    private Stage stage;

    @ManyToOne
    @JoinColumn(name = "defect_group_id")
    private DefectGroup defectGroup;

}
