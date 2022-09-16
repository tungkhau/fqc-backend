package vn.com.assistant.fqcbackend.utility;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import vn.com.assistant.fqcbackend.dto.CustomerResponseDTO;
import vn.com.assistant.fqcbackend.dto.CustomerRequestDTO;
import vn.com.assistant.fqcbackend.entity.Customer;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CustomerMapper extends MapStructMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerResponseDTO customerToCustomerResponseDTO(Customer customer);

    List<CustomerResponseDTO> listCustomerToListCustomerResponseDTO(List<Customer> customerList);

    Customer customerRequestDTOtoCustomer(CustomerRequestDTO customerRequestDTO);

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "name", ignore = true)
    void updateCustomerFromCustomerRequestDTO(CustomerRequestDTO customerRequestDTO, @MappingTarget Customer customer);
}
