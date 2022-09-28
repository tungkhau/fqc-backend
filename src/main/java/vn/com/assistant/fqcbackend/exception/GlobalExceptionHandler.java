package vn.com.assistant.fqcbackend.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.com.assistant.fqcbackend.dto.ResponseBodyDTO;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseBodyDTO unauthorizedException(UnauthorizedException ex) {
        return new ResponseBodyDTO(ex.getMessage(), "UNAUTHORIZED", null);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseBodyDTO conflictException(ConflictException ex) {
        return new ResponseBodyDTO(ex.getMessage(), "CONFLICT", null);
    }

    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseBodyDTO invalidException(InvalidException ex) {
        return new ResponseBodyDTO(ex.getMessage(), "INVALID", null);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseBodyDTO methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseBodyDTO(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), "INVALID", null);
    }


//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ResponseBodyDTO constraintViolationException(ConstraintViolationException ex) {
//        return new ResponseBodyDTO(ex.getMessage(), "INVALID", null);
//    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseBodyDTO dataIntegrityViolationException(DataIntegrityViolationException ex) {
        String constraint = ((ConstraintViolationException) ex.getCause()).getConstraintName();
        return new ResponseBodyDTO(constraint + " đã tồn tại", "INVALID", null);
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseBodyDTO globalExceptionHandler() {
//        return new ResponseBodyDTO("Xay ra loi khong xac dinh", "UNEXPECTED", null);
//    }
}
