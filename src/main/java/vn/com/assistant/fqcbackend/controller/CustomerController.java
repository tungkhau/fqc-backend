package vn.com.assistant.fqcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.dto.CustomerRequestDTO;
import vn.com.assistant.fqcbackend.dto.CustomerResponseDTO;
import vn.com.assistant.fqcbackend.service.CustomerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class CustomerController {
    private final Environment env;

    private final CustomerService customerService;


    @GetMapping(value = "/customers")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseBodyDTO fetch() {
        List<CustomerResponseDTO> customerResponseDTOList = customerService.fetch();
        return new ResponseBodyDTO(null, "ACCEPTED", customerResponseDTOList);
    }

    @PostMapping(value = "/customers")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO create(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        customerService.create(customerRequestDTO);
        return new ResponseBodyDTO(env.getProperty("customer.create"), "OK", null);
    }

    @PutMapping(value = "/customers/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO update(@Valid @RequestBody CustomerRequestDTO customerRequestDTO, @PathVariable String customerId) {
        customerService.update(customerRequestDTO, customerId);
        return new ResponseBodyDTO(env.getProperty("customer.update"), "OK", null);
    }

    @DeleteMapping(value = "/customers/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO delete(@PathVariable String customerId) {
        customerService.delete(customerId);
        return new ResponseBodyDTO(env.getProperty("customer.delete"), "OK", null);
    }

}
