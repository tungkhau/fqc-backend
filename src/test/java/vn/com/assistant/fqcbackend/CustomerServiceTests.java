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
import vn.com.assistant.fqcbackend.entity.Color;
import vn.com.assistant.fqcbackend.entity.Customer;
import vn.com.assistant.fqcbackend.entity.Fabric;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.CustomerRepository;
import vn.com.assistant.fqcbackend.service.imp.CustomerServiceImp;
import vn.com.assistant.fqcbackend.mapper.CustomerMapper;

import java.util.List;
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
    Customer customer;
    @Mock
    Environment env;
    @Mock
    List<Fabric> fabricList;
    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;
    @InjectMocks
    CustomerServiceImp customerService;

    @Test
    void testFetch() {
        customerService.fetch();
        verify(customerRepository).findAllByOrderByCreatedTimeDesc();
    }

    @Test
    void testCreateSuccess() {
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
                .isEqualTo(customer);
    }
    @Test
    void testUpdateSuccess(){
        //give
        CustomerRequestDTO requestDTO = genMockCustomerRequest();
        String customerId = UUID.randomUUID().toString();
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
    void testUpdateFailedNotFound(){
        //given
        CustomerRequestDTO requestDTO = genMockCustomerRequest();
        String customerId = UUID.randomUUID().toString();
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());

        when(env.getProperty("customer.notFound")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> customerService.update(requestDTO, customerId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

        verify(customerRepository, never()).save(any());
    }

    @Test
    void testDeleteSuccess(){
        //give
        given(customerRepository.findById(customer.getId())).willReturn(Optional.of(customer));

        //when
        customerService.delete(customer.getId());
        //then
        Mockito.verify(customerRepository).deleteById(customer.getId());
    }
    @Test
    void testDeleteFailedNotFound(){
        //give
        String customerId = UUID.randomUUID().toString();
        customer.setId(customerId);
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());

        when(env.getProperty("customer.notFound")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> customerService.delete(customerId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

        verify(customerRepository, never()).delete(any());
    }
    @Test
    void testDeleteFailedUsed(){
        //give
        String customerId = UUID.randomUUID().toString();

        customer.setId(customerId);
        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));
        given(customer.getFabricList()).willReturn(fabricList);
        when(env.getProperty("customer.used")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> customerService.delete(customerId))
                .isInstanceOf(ConflictException.class)
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

}
