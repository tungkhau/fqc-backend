package vn.com.assistant.fqcbackend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.CustomerRequestDTO;
import vn.com.assistant.fqcbackend.entity.Customer;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.CustomerRepository;
import vn.com.assistant.fqcbackend.service.CustomerServiceImp;
import vn.com.assistant.fqcbackend.utility.CustomerMapper;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {CustomerServiceImp.class})
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class CustomerServiceTests {
    @Mock
    CustomerRepository customerRepository;
    @Mock
    Environment env;
    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;
    @InjectMocks
    CustomerServiceImp customerService;

    @Test
    void canFetch() {
        customerService.fetch();
        verify(customerRepository).findAllByOrderByCreatedTimeDesc();
    }

    @Test
    void canCreate() {
        //given
        CustomerRequestDTO requestDTO = genMockCustomerRequest();
        customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        Customer customer = CustomerMapper.INSTANCE.customerRequestDTOtoCustomer(requestDTO);

        //when
        customerService.create(requestDTO);
        //then
        Mockito.verify(customerRepository).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(customer);
    }
    @Test
    void canUpdate(){
        //give
        CustomerRequestDTO requestDTO = genMockCustomerRequest();
        String customerId = UUID.randomUUID().toString();
        Customer customer = genMockCustomer();
        customer.setId(customerId);
        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));
        customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        //when
        customerService.update(requestDTO, customerId);

        //then
        Mockito.verify(customerRepository).save(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer).isEqualTo(customer);
    }
    @Test
    void updateFailedNotFound(){
        //given
        CustomerRequestDTO requestDTO = genMockCustomerRequest();
        String customerId = UUID.randomUUID().toString();
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());

        when(env.getProperty("customer.notExisted")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> customerService.update(requestDTO, customerId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

        verify(customerRepository, never()).save(any());
    }

    @Test
    void canDelete(){
        //give
        String customerId = UUID.randomUUID().toString();
        Customer customer = genMockCustomer();
        customer.setId(customerId);
        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));

        //when
        customerService.delete(customerId);
        //then
        Mockito.verify(customerRepository).deleteById(customerId);
    }
    @Test
    void deleteFailedNotFound(){
        //give
        String customerId = UUID.randomUUID().toString();
        Customer customer = genMockCustomer();
        customer.setId(customerId);
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());

        when(env.getProperty("customer.notExisted")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> customerService.delete(customerId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

        verify(customerRepository, never()).delete(any());
    }
    private CustomerRequestDTO genMockCustomerRequest(){
        CustomerRequestDTO requestDTO = new CustomerRequestDTO();
        requestDTO.setCode("CUSTOMER1");
        requestDTO.setAddress("Address request");
        requestDTO.setFullName("Nguyen Van An");
        requestDTO.setName("AnNV");
        requestDTO.setPhoneNumber("123");
        requestDTO.setTaxCode("456");
        return requestDTO;
    }

    private Customer genMockCustomer(){
        Customer customer = new Customer();
        customer.setCode("CUSTOMER");
        customer.setAddress("Address");
        customer.setFullName("Nguyen Van A");
        customer.setName("ANV");
        customer.setPhoneNumber("456");
        customer.setTaxCode("789");
        customer.setColorList(new ArrayList<>());
        customer.setFabricList(new ArrayList<>());
        return customer;
    }
}
