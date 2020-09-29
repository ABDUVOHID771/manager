package com.example.springmanager.controller;

import com.example.springmanager.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<?> handleProjectNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(resourceNotFoundException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<?> handleProjectDepartmentsException(DepartmentsException departmentsException, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(departmentsException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<?> handleUsernameDuplicatedException(UsernameException usernameAlreadyExists, WebRequest webRequest) {
        UsernameAlreadyExists exists = new UsernameAlreadyExists(usernameAlreadyExists.getMessage());
        return new ResponseEntity<>(exists, HttpStatus.BAD_REQUEST);
    }

}
