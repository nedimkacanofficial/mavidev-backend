package com.mavidev.project.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException notFoundException) {
        List<String> detail = new ArrayList<>();
        detail.add(notFoundException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Not Found!", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> alreadyExistException(AlreadyExistException alreadyExistException) {
        List<String> detail=new ArrayList<>();
        detail.add(alreadyExistException.getMessage());
        ErrorResponse errorResponse=new ErrorResponse("Already Exist!",detail);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException dataIntegrityViolationException) {
        List<String> detail=new ArrayList<>();
        detail.add(dataIntegrityViolationException.getMessage());
        ErrorResponse errorResponse=new ErrorResponse("This city is used by another record. Deletion cannot be performed!",detail);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}