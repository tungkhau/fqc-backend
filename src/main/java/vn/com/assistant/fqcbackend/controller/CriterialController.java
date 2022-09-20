package vn.com.assistant.fqcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vn.com.assistant.fqcbackend.dto.CriterialResponseDTO;
import vn.com.assistant.fqcbackend.dto.CustomerResponseDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.service.CriterialService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class CriterialController {
    private final Environment env;

    private final CriterialService criterialService;


    @GetMapping(value = "/criterials")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseBodyDTO fetch() {
        List<CriterialResponseDTO> customerResponseDTOList = criterialService.fetch();
        return new ResponseBodyDTO(null, "ACCEPTED", customerResponseDTOList);
    }
}
