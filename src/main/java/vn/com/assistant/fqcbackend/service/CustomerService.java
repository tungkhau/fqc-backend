package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.CustomerRequestDTO;
import vn.com.assistant.fqcbackend.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerResponseDTO> fetch();

    void create(CustomerRequestDTO customerRequestDTO);

    void update(CustomerRequestDTO customerRequestDTO, String customerId);

    void delete(String customerId);
}
