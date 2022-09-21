package vn.com.assistant.fqcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.com.assistant.fqcbackend.dto.CriteriaRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriteriaResponseDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.service.CriteriaService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class CriteriaController {
    private final Environment env;

    private final CriteriaService criteriaService;

    @GetMapping(value = "/criteria")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseBodyDTO fetch() {
        List<CriteriaResponseDTO> customerResponseDTOList = criteriaService.fetch();
        return new ResponseBodyDTO(null, "ACCEPTED", customerResponseDTOList);
    }

    @PostMapping(value = "/criteria")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO create(@Valid @RequestBody CriteriaRequestDTO criteriaRequestDTO){
        criteriaService.create(criteriaRequestDTO);
        return new ResponseBodyDTO(env.getProperty("criteria.created"), "OK", null);
    }

    @DeleteMapping(value = "/criteria/{criteriaId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO delete(@PathVariable String criteriaId){
        criteriaService.delete(criteriaId);
        return new ResponseBodyDTO(env.getProperty("criteria.deleted"), "OK", null);
    }
}
