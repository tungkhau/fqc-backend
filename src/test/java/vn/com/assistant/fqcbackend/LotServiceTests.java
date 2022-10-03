package vn.com.assistant.fqcbackend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.LotRequestDTO;
import vn.com.assistant.fqcbackend.entity.Lot;
import vn.com.assistant.fqcbackend.entity.Product;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.LotRepository;
import vn.com.assistant.fqcbackend.repository.ProductRepository;
import vn.com.assistant.fqcbackend.service.imp.LotServiceImp;
import vn.com.assistant.fqcbackend.mapper.LotMapper;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {LotServiceImp.class})
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class LotServiceTests {
    @Mock
    LotRepository lotRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    Product product;
    @Mock
    Environment env;
    @Mock
    Lot lot;
    @Captor
    private ArgumentCaptor<Lot> lotArgumentCaptor;
    @InjectMocks
    LotServiceImp lotService;

    @Test
    void canFetch() {
        lotService.fetch();
        verify(lotRepository).findAllByOrderByCreatedTimeDesc();
    }

    @Test
    void canCreate() {
        //given
        LotRequestDTO requestDTO = genMockLotRequest();
        lotArgumentCaptor = ArgumentCaptor.forClass(Lot.class);
        Lot lot = LotMapper.INSTANCE.lotRequestDTOtoLot(requestDTO);
        given(productRepository.findById(requestDTO.getProductId())).willReturn(Optional.of(product));
        lot.setProduct(product);

        //when
        lotService.create(requestDTO);
        //then
        Mockito.verify(lotRepository).save(lotArgumentCaptor.capture());
        Lot capturedLot = lotArgumentCaptor.getValue();

        assertThat(capturedLot)
                .usingRecursiveComparison()
                .isEqualTo(lot);
    }
    @Test
    void canUpdate(){
        //give
        LotRequestDTO requestDTO = genMockLotRequest();
        String lotId = UUID.randomUUID().toString();
        lot.setId(lotId);
        given(lotRepository.findById(lotId)).willReturn(Optional.of(lot));
        lotArgumentCaptor = ArgumentCaptor.forClass(Lot.class);

        //when
        lotService.update(requestDTO, lotId);

        //then
        Mockito.verify(lotRepository).save(lotArgumentCaptor.capture());
        Lot capturedLot = lotArgumentCaptor.getValue();
        assertThat(capturedLot).isEqualTo(lot);
    }
    @Test
    void updateFailedNotFound(){
        //given
        LotRequestDTO requestDTO = genMockLotRequest();
        String lotId = UUID.randomUUID().toString();
        given(lotRepository.findById(lotId)).willReturn(Optional.empty());

        when(env.getProperty("lot.notExisted")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> lotService.update(requestDTO, lotId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

        verify(lotRepository, never()).save(any());
    }

    @Test
    void canDelete(){
        //give
        given(lotRepository.findById(lot.getId())).willReturn(Optional.of(lot));

        //when
        lotService.delete(lot.getId());
        //then
        Mockito.verify(lotRepository).deleteById(lot.getId());
    }
    @Test
    void deleteFailedNotFound(){
        //given
        String lotId = UUID.randomUUID().toString();
        given(lotRepository.findById(lotId)).willReturn(Optional.empty());

        when(env.getProperty("lot.notExisted")).thenReturn("Msg");
        //when and then
        Assertions.assertThatThrownBy(()-> lotService.delete(lotId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");

        verify(lotRepository, never()).delete(any());
    }
    private LotRequestDTO genMockLotRequest(){
        LotRequestDTO requestDTO = new LotRequestDTO();
        requestDTO.setCode("Code request");
        requestDTO.setExpectedQuantity(1);
        requestDTO.setExpectedWeight(2);
        requestDTO.setOrderCode("3");
        return requestDTO;
    }
}