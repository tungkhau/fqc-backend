package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.ProductRequestDTO;
import vn.com.assistant.fqcbackend.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> fetch();

    void create(ProductRequestDTO productRequestDTO);

    void update(ProductRequestDTO productRequestDTO, String productId);

    void delete(String productId);
}
