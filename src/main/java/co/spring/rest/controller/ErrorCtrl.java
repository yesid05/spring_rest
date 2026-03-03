package co.spring.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import co.spring.rest.error.CreatedError;
import co.spring.rest.error.ErrorMesssage;
import co.spring.rest.error.NotFoundError;

@ControllerAdvice
public class ErrorCtrl {

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
    protected ResponseEntity<?> errorArgumentNotValid(MethodArgumentNotValidException exception){
        
        Map errorMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        ErrorMesssage exceptionMesssage = new ErrorMesssage(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), errorMap);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionMesssage);
    }
}
