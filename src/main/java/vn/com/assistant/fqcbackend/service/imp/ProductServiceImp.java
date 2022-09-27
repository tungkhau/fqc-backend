package vn.com.assistant.fqcbackend.service.imp;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.ProductRequestDTO;
import vn.com.assistant.fqcbackend.dto.ProductResponseDTO;
import vn.com.assistant.fqcbackend.entity.*;
import vn.com.assistant.fqcbackend.entity.enums.Label;
import vn.com.assistant.fqcbackend.exception.ConflictException;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.ColorRepository;
import vn.com.assistant.fqcbackend.repository.CriterionRepository;
import vn.com.assistant.fqcbackend.repository.FabricRepository;
import vn.com.assistant.fqcbackend.repository.ProductRepository;
import vn.com.assistant.fqcbackend.service.ProductService;
import vn.com.assistant.fqcbackend.mapper.ProductMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    private final FabricRepository fabricRepository;

    private final ColorRepository colorRepository;

    private final CriterionRepository criterionRepository;

    private final Environment env;

    @Override
    public List<ProductResponseDTO> fetch() {
        List<Product> productList = productRepository.findAllByOrderByCreatedTimeDesc();
        return ProductMapper.INSTANCE.listProductToListProductResponseDTO(productList);
    }

    @Override
    public void create(ProductRequestDTO productRequestDTO) {
        Product product = ProductMapper.INSTANCE.productRequestDTOtoProduct(productRequestDTO);

        Fabric fabric = fabricRepository.findById(productRequestDTO.getFabricId()).orElseThrow(() -> new InvalidException(env.getProperty("fabric.notExisted")));
        Color color = colorRepository.findById(productRequestDTO.getColorId()).orElseThrow(() -> new InvalidException(env.getProperty("color.notExisted")));

        boolean sameCustomer = fabric.getCustomer().equals(color.getCustomer());
        if (!sameCustomer) throw new ConflictException(env.getProperty("product.create.notSameCustomer"));

        product.setFabric(fabric);
        product.setColor(color);
        productRepository.save(product);
    }

    @Override
    public void update(ProductRequestDTO productRequestDTO, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new InvalidException(env.getProperty("product.notExisted")));

        boolean validLabel = EnumUtils.isValidEnum(Label.class, productRequestDTO.getLabel());
        if (!validLabel) throw new InvalidException(env.getProperty("product.create.invalidLabel"));
        Label label = Label.valueOf(productRequestDTO.getLabel());

        Criterion criterion = criterionRepository.findById(productRequestDTO.getCriterionId()).orElseThrow(() -> new InvalidException(env.getProperty("criterion.notExisted")));
        product.setCriterion(criterion);
        product.setLabel(label);
        productRepository.save(product);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new InvalidException(env.getProperty("product.notExisted")));
        if (!(product.getLotList().isEmpty())) throw new ConflictException(env.getProperty("product.used"));
        productRepository.deleteById(productId);
    }
}
