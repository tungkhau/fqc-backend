package vn.com.assistant.fqcbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.com.assistant.fqcbackend.dto.PasswordRequestDTO;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;
import vn.com.assistant.fqcbackend.dto.UserRequestDTO;
import vn.com.assistant.fqcbackend.service.AuthService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class AuthController {
    private final AuthService authService;
    private final Environment env;

    @PostMapping("/auth/login")
    public ResponseBodyDTO login(@RequestBody UserRequestDTO requestDTO){
        return authService.login(requestDTO);
    }

    @PostMapping(value = "/auth/{staffId}/changePassword")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseBodyDTO changePassword(@Valid @RequestBody PasswordRequestDTO passwordRequestDTO,
                                          @PathVariable String staffId) {
        authService.changePassword(passwordRequestDTO, staffId);
        return new ResponseBodyDTO(env.getProperty("staff.changePassword"), "OK", null);
    }
}
