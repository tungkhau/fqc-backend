package vn.com.assistant.fqcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.com.assistant.fqcbackend.dto.CriterialRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriterialResponseDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.service.CriterialService;

import javax.validation.Valid;
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

    @PostMapping(value = "/criterials")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO create(@Valid @RequestBody CriterialRequestDTO criterialRequestDTO){
        criterialService.create(criterialRequestDTO);
        return new ResponseBodyDTO(env.getProperty("criterial.created"), "OK", null);
    }

    @DeleteMapping(value = "/criterials/{criterialId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO delete(@PathVariable String id){
        criterialService.delete(id);
        return new ResponseBodyDTO(env.getProperty("criterial.deleted"), "OK", null);
    }
}
