package vn.com.assistant.fqcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.com.assistant.fqcbackend.dto.FabricRequestDTO;
import vn.com.assistant.fqcbackend.dto.FabricResponseDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.service.FabricService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class FabricController {
    private final Environment env;

    private final FabricService fabricService;


    @GetMapping(value = "/fabrics")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseBodyDTO fetch() {
        List<FabricResponseDTO> fabricResponseDTOList = fabricService.fetch();
        return new ResponseBodyDTO(null, "ACCEPTED", fabricResponseDTOList);
    }

    @PostMapping(value = "/fabrics")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO create(@Valid @RequestBody FabricRequestDTO fabricRequestDTO) {
        fabricService.create(fabricRequestDTO);
        return new ResponseBodyDTO(env.getProperty("fabric.create"), "OK", null);
    }
    
    @DeleteMapping(value = "/fabrics/{fabricId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO delete(@PathVariable String fabricId) {
        fabricService.delete(fabricId);
        return new ResponseBodyDTO(env.getProperty("fabric.delete"), "OK", null);
    }

}
