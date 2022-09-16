package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerResponseDTO {
    private String id;
    private String code;
    private String name;
    private String fullName;
    private String address;
    private String taxCode;
    private String phoneNumber;
}
