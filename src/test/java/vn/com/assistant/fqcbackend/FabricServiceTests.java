package vn.com.assistant.fqcbackend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.ColorRequestDTO;
import vn.com.assistant.fqcbackend.dto.FabricRequestDTO;
import vn.com.assistant.fqcbackend.entity.Color;
import vn.com.assistant.fqcbackend.entity.Customer;
import vn.com.assistant.fqcbackend.entity.Fabric;
import vn.com.assistant.fqcbackend.entity.Product;
import vn.com.assistant.fqcbackend.entity.enums.Label;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.mapper.ColorMapper;
import vn.com.assistant.fqcbackend.mapper.FabricMapper;
import vn.com.assistant.fqcbackend.repository.CustomerRepository;
import vn.com.assistant.fqcbackend.repository.FabricRepository;
import vn.com.assistant.fqcbackend.service.imp.FabricServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {FabricServiceImp.class})
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class FabricServiceTests {
    @Mock
    FabricRepository fabricRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    Customer customer;
    @Mock
    Fabric fabric;
    @Mock
    List<Product> products;
    @Mock
    Environment env;
    @Captor
    private ArgumentCaptor<Fabric> fabricArgumentCaptor;
    @InjectMocks
    FabricServiceImp fabricService;

    @Test
    void testFetch() {
        fabricService.fetch();
        verify(fabricRepository).findAllByOrderByCreatedTimeDesc();
    }

    @Test
    void testCreatedSuccess(){
        //given
        FabricRequestDTO requestDTO = genMockFabricRequest();
        Fabric fabric = FabricMapper.INSTANCE.fabricRequestDTOtoFabric(requestDTO);
        fabricArgumentCaptor = ArgumentCaptor.forClass(Fabric.class);
        given(customerRepository.findById(requestDTO.getCustomerId())).willReturn(Optional.of(customer));
        fabric.setCustomer(customer);

        //when
        fabricService.create(requestDTO);

        //then
        Mockito.verify(fabricRepository).save(fabricArgumentCaptor.capture());
        Fabric capturedColor = fabricArgumentCaptor.getValue();

        assertThat(capturedColor)
                .usingRecursiveComparison()
                .isEqualTo(fabric);
    }

    @Test
    void testCreatedFailedInvalidCustomer(){
        //given
        FabricRequestDTO requestDTO = genMockFabricRequest();
        given(customerRepository.findById(requestDTO.getCustomerId())).willReturn(Optional.empty());
        when(env.getProperty("customer.notFound")).thenReturn("Msg");

        //when & then
        assertThatThrownBy(() -> fabricService.create(requestDTO))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testDeleteSuccess(){
        //given
        given(fabricRepository.findById(fabric.getId())).willReturn(Optional.of(fabric));
        //when
        fabricService.delete(fabric.getId());
        //then
        verify(fabricRepository).deleteById(fabric.getId());
    }

    @Test
    void testDeleteFailedNotFound(){
        //given
        String fabricId = "Test id";
        given(fabricRepository.findById(fabricId)).willReturn(Optional.empty());
        when(env.getProperty("fabric.notFound")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(()-> fabricService.delete(fabricId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
    }


    @Test
    void testDeleteFailedUsed(){
        //given
        given(fabricRepository.findById(fabric.getId())).willReturn(Optional.of(fabric));
        given(fabric.getProductList()).willReturn(products);
        when(env.getProperty("fabric.used")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(()-> fabricService.delete(fabric.getId()))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Msg");
    }

    private FabricRequestDTO genMockFabricRequest(){
        FabricRequestDTO requestDTO = new FabricRequestDTO();
        requestDTO.setCode("Test id");
        requestDTO.setName("Fabricname test");
        requestDTO.setCustomerId("123");
        return requestDTO;
    }

}
