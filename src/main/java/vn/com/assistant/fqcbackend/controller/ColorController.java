package vn.com.assistant.fqcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.com.assistant.fqcbackend.dto.ColorRequestDTO;
import vn.com.assistant.fqcbackend.dto.ColorResponseDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.service.ColorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class ColorController {
    private final Environment env;

    private final ColorService colorService;


    @GetMapping(value = "/colors")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseBodyDTO fetch() {
        List<ColorResponseDTO> colorResponseDTOList = colorService.fetch();
        return new ResponseBodyDTO(null, "ACCEPTED", colorResponseDTOList);
    }

    @PostMapping(value = "/colors")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO create(@Valid @RequestBody ColorRequestDTO colorRequestDTO) {
        colorService.create(colorRequestDTO);
        return new ResponseBodyDTO(env.getProperty("color.create"), "OK", null);
    }
    
    @DeleteMapping(value = "/colors/{colorId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO delete(@PathVariable String colorId) {
        colorService.delete(colorId);
        return new ResponseBodyDTO(env.getProperty("color.delete"), "OK", null);
    }

}
