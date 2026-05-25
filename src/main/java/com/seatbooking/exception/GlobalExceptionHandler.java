package com.seatbooking.exception;

import org.springframework.http.HttpStatus; //importing necessary classes for handling HTTP status codes
import org.springframework.http.ResponseEntity; //importing ResponseEntity for returning responses from exception handlers
import org.springframework.web.bind.MethodArgumentNotValidException; //importing exception class for handling validation errors
import org.springframework.web.bind.annotation.ControllerAdvice; //importing annotation for global exception handling

import org.springframework.web.bind.annotation.*; //importing necessary annotations for handling specific exceptions

import java.util.HashMap; //importing HashMap for storing error messages
import java.util.Map; //importing Map interface for defining the type of error messages

@RestControllerAdvice //annotation to indicate that this class will handle exceptions globally for REST controllers
public class GlobalExceptionHandler {

    //Validation exception handler for handling validation errors and returning appropriate error messages
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(

        MethodArgumentNotValidException ex){

            Map<String, String> errors = new HashMap<>(); //creating a HashMap to store field names and corresponding error messages

            ex.getBindingResult().getFieldErrors().forEach(error -> {

                errors.put(

                    error.getField(),
                    error.getDefaultMessage()
                );
            });

        Map<String, Object> response = new HashMap<>(); //creating a HashMap to store the overall response structure

        response.put("message", "Validation failed"); //adding a message to the response indicating that validation has failed
        response.put("errors", errors); //adding the field-specific error messages to the response

        return new ResponseEntity<>(
            response, HttpStatus.BAD_REQUEST 
        ); //returning the response entity with the error details and a BAD_REQUEST status code

        }

}
