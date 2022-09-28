package vn.com.assistant.fqcbackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.assistant.fqcbackend.dto.ProductRequestDTO;
import vn.com.assistant.fqcbackend.dto.ProductResponseDTO;
import vn.com.assistant.fqcbackend.entity.Product;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductMapper extends MapStructMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "fabricName", source = "fabric.name")
    @Mapping(target = "fabricCode", source = "fabric.code")
    @Mapping(target = "colorName", source = "color.name")
    @Mapping(target = "colorCode", source = "color.code")
    @Mapping(target = "criterionName", source = "criterion.name")
    ProductResponseDTO productToProductResponseDTO(Product product);

    List<ProductResponseDTO> listProductToListProductResponseDTO(List<Product> productList);

    @Mapping(target = "label", ignore = true)
    Product productRequestDTOtoProduct(ProductRequestDTO productRequestDTO);
}
