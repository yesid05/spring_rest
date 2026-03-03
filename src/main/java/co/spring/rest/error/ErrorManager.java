package co.spring.rest.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorManager {

    @ExceptionHandler(NotFoundError.class)
    protected ResponseEntity<ErrorMesssage> errorUserNotFound(NotFoundError exception){
        ErrorMesssage exceptionMessage = new ErrorMesssage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), exception.getDescription());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionMessage);
    }

    @ExceptionHandler(CreatedError.class)
    protected ResponseEntity<ErrorMesssage> errorUserCreated(CreatedError exception){
        ErrorMesssage exceptionMesssage = new ErrorMesssage(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), exception.getDescription());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionMesssage);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> errorArgumentNotValid(MethodArgumentNotValidException e){
        
        Map errorMap = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        ErrorMesssage exceptionMesssage = new ErrorMesssage(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), errorMap);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionMesssage);
    }
}
