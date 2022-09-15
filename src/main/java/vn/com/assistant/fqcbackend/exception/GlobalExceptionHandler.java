package vn.com.assistant.fqcbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseBodyDTO conflictException(UnauthorizedException ex) {
        return new ResponseBodyDTO(ex.getMessage(), "UNAUTHORIZED", null);
    }
}
