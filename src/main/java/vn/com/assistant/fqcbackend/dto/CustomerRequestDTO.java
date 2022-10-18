package vn.com.assistant.fqcbackend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
public class CustomerRequestDTO {
    @Size(min = 3, max = 3, message = "{customer.code.size}")
    private String code;
    @Size(min = 1, max = 16, message = "{customer.name.size}")
    private String name;
    @Size(min = 1, max = 50, message = "{customer.fullName.size}")
    private String fullName;
    @Size(min = 1, max = 100, message = "{customer.address.size}")
    private String address;
    @Size(min = 1, max = 15, message = "{customer.taxCode.size}")
    private String taxCode;
    @Size(min = 1, max = 20, message = "{customer.phoneNumber.size}")
    private String phoneNumber;
}
