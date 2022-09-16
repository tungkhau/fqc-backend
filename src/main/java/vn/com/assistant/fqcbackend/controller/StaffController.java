package vn.com.assistant.fqcbackend.controller;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.dto.StaffRequestDTO;
import vn.com.assistant.fqcbackend.service.StaffService;

import javax.validation.Valid;

@RestController
public class StaffController {
    private final StaffService staffService;

    private final Environment env;

    public StaffController(StaffService staffService, Environment env) {
        this.staffService = staffService;
        this.env = env;
    }

    @PostMapping(value = "/staffs")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO create(@Valid @RequestBody StaffRequestDTO staffRequestDTO) {
        staffService.create(staffRequestDTO);
        return new ResponseBodyDTO(env.getProperty("staff.create"), "OK", null);
    }

    @PutMapping(value = "/staffs/{staffId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO update(@Valid @RequestBody StaffRequestDTO staffRequestDTO, @PathVariable String staffId) {
        staffService.update(staffRequestDTO, staffId);
        return new ResponseBodyDTO(env.getProperty("staff.update"), "OK", null);
    }

    @PatchMapping(value = "/staffs/{staffId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO resetPassword(@PathVariable String staffId) {
        staffService.resetPassword(staffId);
        return new ResponseBodyDTO(env.getProperty("staff.resetPassword"), "OK", null);
    }

}
