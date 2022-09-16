package vn.com.assistant.fqcbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.CustomerRequestDTO;
import vn.com.assistant.fqcbackend.dto.CustomerResponseDTO;
import vn.com.assistant.fqcbackend.entity.Customer;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.CustomerRepository;
import vn.com.assistant.fqcbackend.utility.CustomerMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;

    private final Environment env;

    @Override
    public List<CustomerResponseDTO> fetch() {
        List<Customer> customerList = customerRepository.findAll();
        return CustomerMapper.INSTANCE.listCustomerToListCustomerResponseDTO(customerList);
    }

    @Override
    public void create(CustomerRequestDTO customerRequestDTO) {
        Customer customer = CustomerMapper.INSTANCE.customerRequestDTOtoCustomer(customerRequestDTO);
        customerRepository.save(customer);
    }

    @Override
    public void update(CustomerRequestDTO customerRequestDTO, String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new InvalidException(env.getProperty("customer.notExisted")));
        CustomerMapper.INSTANCE.updateCustomerFromCustomerRequestDTO(customerRequestDTO, customer);
        customerRepository.save(customer);
    }

    @Override
    public void delete(String customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> new InvalidException(env.getProperty("customer.notExisted")));
        customerRepository.deleteById(customerId);
    }
}
