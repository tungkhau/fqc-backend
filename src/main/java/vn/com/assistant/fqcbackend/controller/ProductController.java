package vn.com.assistant.fqcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.com.assistant.fqcbackend.dto.ProductRequestDTO;
import vn.com.assistant.fqcbackend.dto.ProductResponseDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class ProductController {
    private final Environment env;

    private final ProductService productService;


    @GetMapping(value = "/products")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseBodyDTO fetch() {
        List<ProductResponseDTO> productResponseDTOList = productService.fetch();
        return new ResponseBodyDTO(null, "ACCEPTED", productResponseDTOList);
    }

    @PostMapping(value = "/products")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO create(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        productService.create(productRequestDTO);
        return new ResponseBodyDTO(env.getProperty("product.create"), "OK", null);
    }

    @PutMapping(value = "/products/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO update(@Valid @RequestBody ProductRequestDTO productRequestDTO, @PathVariable String productId) {
        productService.update(productRequestDTO, productId);
        return new ResponseBodyDTO(env.getProperty("product.update"), "OK", null);
    }

    @DeleteMapping(value = "/products/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO delete(@PathVariable String productId) {
        productService.delete(productId);
        return new ResponseBodyDTO(env.getProperty("product.delete"), "OK", null);
    }

}
