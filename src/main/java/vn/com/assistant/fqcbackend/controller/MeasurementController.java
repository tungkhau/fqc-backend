package vn.com.assistant.fqcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.com.assistant.fqcbackend.dto.MeasurementRequestDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.service.MeasurementService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final Environment env;
    

    @PostMapping(value = "/measurements")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO create(@Valid @RequestBody MeasurementRequestDTO measurementRequestDTO) {
        measurementService.create(measurementRequestDTO);
        return new ResponseBodyDTO(env.getProperty("measurement.created"), "OK", null);
    }

    @DeleteMapping(value = "/measurements/{measurementId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO delete(@PathVariable String measurementId) {
        measurementService.delete(measurementId);
        return new ResponseBodyDTO(env.getProperty("measurement.deleted"), "OK", null);
    }
}
