package vn.com.assistant.fqcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.com.assistant.fqcbackend.dto.LotRequestDTO;
import vn.com.assistant.fqcbackend.dto.LotResponseDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.service.LotService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class LotController {
    private final LotService lotService;
    private final Environment env;

    @GetMapping(value = "/lots")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseBodyDTO fetch() {
        List<LotResponseDTO> lotResponseDTOList = lotService.fetch();
        return new ResponseBodyDTO(null, "ACCEPTED", lotResponseDTOList);
    }

    @GetMapping(value = "/lots/{lotCode}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseBodyDTO get(@PathVariable String lotCode) {
        LotResponseDTO lotResponseDTO = lotService.get(lotCode);
        return new ResponseBodyDTO(null, "ACCEPTED", lotResponseDTO);
    }

    @PostMapping(value = "/lots")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO create(@Valid @RequestBody LotRequestDTO lotRequestDTO) {
        lotService.create(lotRequestDTO);
        return new ResponseBodyDTO(env.getProperty("lot.created"), "OK", null);
    }

    @PutMapping(value = "/lots/{lotId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO update(@Valid @RequestBody LotRequestDTO lotRequestDTO, @PathVariable String lotId) {
        lotService.update(lotRequestDTO, lotId);
        return new ResponseBodyDTO(env.getProperty("lot.updated"), "OK", null);
    }

    @DeleteMapping(value = "/lots/{lotId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO delete(@PathVariable String lotId) {
        lotService.delete(lotId);
        return new ResponseBodyDTO(env.getProperty("lot.deleted"), "OK", null);
    }
}
