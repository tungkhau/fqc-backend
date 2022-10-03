package vn.com.assistant.fqcbackend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import vn.com.assistant.fqcbackend.dto.ProductRequestDTO;
import vn.com.assistant.fqcbackend.entity.*;
import vn.com.assistant.fqcbackend.entity.enums.Label;
import vn.com.assistant.fqcbackend.entity.enums.Unit;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.mapper.ProductMapper;
import vn.com.assistant.fqcbackend.repository.ColorRepository;
import vn.com.assistant.fqcbackend.repository.CriterionRepository;
import vn.com.assistant.fqcbackend.repository.FabricRepository;
import vn.com.assistant.fqcbackend.repository.ProductRepository;
import vn.com.assistant.fqcbackend.service.imp.ProductServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ProductServiceImp.class})
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class ProductServiceTests {
    @Mock
    ProductRepository productRepository;
    @Mock
    ColorRepository colorRepository;
    @Mock
    FabricRepository fabricRepository;
    @Mock
    CriterionRepository criterionRepository;
    @Mock
    Product product;
    @Mock
    Customer customer;
    @Mock
    Criterion criterion;
    @Mock
    List<Lot> lots;
    @Mock
    Environment env;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;
    @InjectMocks
    ProductServiceImp productService;

    @Test
    void testFetch() {
        productService.fetch();
        verify(productRepository).findAllByOrderByCreatedTimeDesc();
    }

    @Test
    void testCreateSuccess() {
        //given
        ProductRequestDTO requestDTO = genMockProductRequest();
        Product product = ProductMapper.INSTANCE.productRequestDTOtoProduct(requestDTO);

        Fabric fabric = genMockFabric();
        Color color = genMockColor();

        fabric.setCustomer(customer);
        color.setCustomer(customer);

        given(fabricRepository.findById(requestDTO.getFabricId())).willReturn(Optional.of(fabric));
        given(colorRepository.findById(requestDTO.getColorId())).willReturn(Optional.of(color));
        productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        product.setColor(color);
        product.setFabric(fabric);

        //when
        productService.create(requestDTO);
        //then
        Mockito.verify(productRepository).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();

        assertThat(capturedProduct)
                .usingRecursiveComparison()
                .isEqualTo(product);

    }


    @Test
    void testCreateFailedDifferentCustomer() {
        //given
        ProductRequestDTO requestDTO = genMockProductRequest();
        Fabric fabric = genMockFabric();
        Color color = genMockColor();

        fabric.setCustomer(customer);

        given(fabricRepository.findById(requestDTO.getFabricId())).willReturn(Optional.of(fabric));
        given(colorRepository.findById(requestDTO.getColorId())).willReturn(Optional.of(color));

        when(env.getProperty("product.create.notSameCustomer")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(() -> productService.create(requestDTO))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testCreateFailedInvalidColor() {
        //given
        ProductRequestDTO requestDTO = genMockProductRequest();
        Fabric fabric = genMockFabric();

        given(fabricRepository.findById(requestDTO.getFabricId())).willReturn(Optional.of(fabric));
        given(colorRepository.findById(requestDTO.getColorId())).willReturn(Optional.empty());

        when(env.getProperty("color.notFound")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(() -> productService.create(requestDTO))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testCreateFailedInvalidFabric() {
        //given
        ProductRequestDTO requestDTO = genMockProductRequest();

        given(fabricRepository.findById(requestDTO.getFabricId())).willReturn(Optional.empty());

        when(env.getProperty("fabric.notFound")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(() -> productService.create(requestDTO))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testUpdateFailedNotFound() {
        //given
        ProductRequestDTO requestDTO = genMockProductRequest();
        String productId = "test id";
        given(productRepository.findById(productId)).willReturn(Optional.empty());

        when(env.getProperty("product.notFound")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(() -> productService.update(requestDTO, productId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testUpdateFailedInvalidLabel() {
        //given
        ProductRequestDTO requestDTO = genMockProductRequest();
        requestDTO.setLabel("Test");
        String productId = "Test id";

        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        when(env.getProperty("product.create.invalidLabel")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(() -> productService.update(requestDTO, productId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testUpdateFailedInvalidCriteria() {
        //given
        ProductRequestDTO requestDTO = genMockProductRequest();
        String productId = "Test id";

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(criterionRepository.findById(requestDTO.getCriterionId())).willReturn(Optional.empty());

        when(env.getProperty("criterion.notFound")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(() -> productService.update(requestDTO, productId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testUpdateSuccess() {
        //given
        ProductRequestDTO requestDTO = genMockProductRequest();
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(criterionRepository.findById(requestDTO.getCriterionId())).willReturn(Optional.of(criterion));

        productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        //when
        productService.update(requestDTO, product.getId());
        Mockito.verify(productRepository).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();

        assertThat(product).isEqualTo(capturedProduct);
    }

    @Test
    void testDeleteFailedNotFound() {
        //given
        given(productRepository.findById(product.getId())).willReturn(Optional.empty());

        when(env.getProperty("product.notFound")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(() -> productService.delete(product.getId()))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testDeleteFailedUsed() {
        //given
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(product.getLotList()).willReturn(lots);

        when(env.getProperty("product.used")).thenReturn("Msg");
        //when & then
        assertThatThrownBy(() -> productService.delete(product.getId()))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Msg");
    }

    @Test
    void testDeleteSuccess() {
        //given
        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        //when
        productService.delete(product.getId());
        //then
        verify(productRepository).deleteById(product.getId());
    }

    private ProductRequestDTO genMockProductRequest() {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setColorId("Color id");
        requestDTO.setFabricId("Fabric id");
        requestDTO.setCriterionId("Criterion id");
        requestDTO.setLabel(Label.FIRST.name());
        return requestDTO;
    }

    private Color genMockColor(){
        return new Color("Color id", "color test", "COLOR1", new ArrayList<>(), null, null);
    }

    private Fabric genMockFabric(){
        return new Fabric("Fabric id", "fabric test", "FABRIC1", new ArrayList<>(), null, null);
    }
}
