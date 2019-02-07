package com.cvt.springbootunittesting.ExceptionHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CvtResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    //Method to handle All Exception
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){

        ExceptionResponseFormat exceptionResponseFormat=new ExceptionResponseFormat(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity(exceptionResponseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponseFormat exceptionResponseFormat=new ExceptionResponseFormat(new Date(),"User Validation Failed !",ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponseFormat, HttpStatus.BAD_REQUEST);
    }

    //Custom UserNotFound Exception
    @ExceptionHandler(StudentNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(StudentNotFoundException ex, WebRequest request){
        ExceptionResponseFormat exceptionResponseFormat =
        new ExceptionResponseFormat(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity(exceptionResponseFormat, HttpStatus.NOT_FOUND);
    }
}
