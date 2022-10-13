package vn.com.assistant.fqcbackend.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class InspectingSessionResponseDTO {
    private String id;
    private String code;
    private String machineNo;
    private Date startTime;
    private Date endTime;
    private Float weight;
    private Integer grade;
    private boolean accepted;
    private Integer no;
    private List<SessionDefectResponseDTO> sessionDefectResponseDTOList;
    private String customerName;
    private String lotCode;
    private String lotOrderCode;
    private String fabricName;
    private String fabricCode;
    private String colorCode;
    private String colorName;
    private String userId;
    private String userName;
}
