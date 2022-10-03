package vn.com.assistant.fqcbackend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.ColorRequestDTO;
import vn.com.assistant.fqcbackend.entity.Color;
import vn.com.assistant.fqcbackend.entity.Customer;
import vn.com.assistant.fqcbackend.entity.Product;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.mapper.ColorMapper;
import vn.com.assistant.fqcbackend.repository.ColorRepository;
import vn.com.assistant.fqcbackend.repository.CustomerRepository;
import vn.com.assistant.fqcbackend.service.imp.ColorServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ColorServiceImp.class})
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class ColorServiceTests {
    @Mock
    ColorRepository colorRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    Environment env;
    @Mock
    Color color;
    @Mock
    Customer customer;
    @Mock
    List<Product> productList;
    @Captor
    private ArgumentCaptor<Color> colorArgumentCaptor;
    @Spy
    @InjectMocks
    ColorServiceImp colorService;

    @Test
    void testFetch() {
        colorService.fetch();
        verify(colorRepository).findAllByOrderByCreatedTimeDesc();
    }

    @Test
    void testCreatedSuccess(){
        //given
        ColorRequestDTO requestDTO = genMockColorRequest();
        Color color = ColorMapper.INSTANCE.colorRequestDTOtoColor(requestDTO);
        colorArgumentCaptor = ArgumentCaptor.forClass(Color.class);
        given(customerRepository.findById(requestDTO.getCustomerId())).willReturn(Optional.of(customer));
        color.setCustomer(customer);

        //when
        colorService.create(requestDTO);

        //then
        Mockito.verify(colorRepository).save(colorArgumentCaptor.capture());
        Color capturedColor = colorArgumentCaptor.getValue();

        assertThat(capturedColor)
                .usingRecursiveComparison()
                .isEqualTo(color);
    }

    @Test
    void testCreatedFailedInvalidCustomer(){
        //given
        ColorRequestDTO requestDTO = genMockColorRequest();
        colorArgumentCaptor = ArgumentCaptor.forClass(Color.class);
        given(customerRepository.findById(requestDTO.getCustomerId())).willReturn(Optional.empty());
        when(env.getProperty("customer.notFound")).thenReturn("Msg");

        //when & then
        assertThatThrownBy(() -> colorService.create(requestDTO))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testDeleteSuccess(){
        //given
        String colorId = "Test id";
        given(colorRepository.findById(colorId)).willReturn(Optional.of(color));
        //when
        colorService.delete(colorId);
        //then
        verify(colorRepository).deleteById(colorId);
    }

    @Test
    void testDeleteFailedNotFound(){
        //given
        String colorId = "Test id";
        given(colorRepository.findById(colorId)).willReturn(Optional.empty());

        when(env.getProperty("color.notFound")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(()-> colorService.delete(colorId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
    }


    @Test
    void testDeleteFailedUsed(){
        //given
        given(colorRepository.findById(color.getId())).willReturn(Optional.of(color));
        given(color.getProductList()).willReturn(productList);

        when(env.getProperty("color.used")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(()-> colorService.delete(color.getId()))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Msg");
    }

    private ColorRequestDTO genMockColorRequest(){
        ColorRequestDTO requestDTO = new ColorRequestDTO();
        requestDTO.setCode("Test id");
        requestDTO.setName("Colorname test");
        requestDTO.setCustomerId("123");
        return requestDTO;
    }

}
