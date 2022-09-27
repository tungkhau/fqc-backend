package vn.com.assistant.fqcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.com.assistant.fqcbackend.dto.CriterionRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriterionResponseDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.service.CriterionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class CriterionController {
    private final Environment env;

    private final CriterionService criterionService;

    @GetMapping(value = "/criteria")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseBodyDTO fetch() {
        List<CriterionResponseDTO> customerResponseDTOList = criterionService.fetch();
        return new ResponseBodyDTO(null, "ACCEPTED", customerResponseDTOList);
    }

    @PostMapping(value = "/criteria")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO create(@Valid @RequestBody CriterionRequestDTO criterionRequestDTO){
        criterionService.create(criterionRequestDTO);
        return new ResponseBodyDTO(env.getProperty("criterion.created"), "OK", null);
    }

    @DeleteMapping(value = "/criteria/{criterionId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO delete(@PathVariable String criterionId){
        criterionService.delete(criterionId);
        return new ResponseBodyDTO(env.getProperty("criterion.deleted"), "OK", null);
    }
}
