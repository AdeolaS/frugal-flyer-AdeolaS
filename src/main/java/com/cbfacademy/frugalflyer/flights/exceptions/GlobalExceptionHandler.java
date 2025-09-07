package com.cbfacademy.frugalflyer.flights.exceptions;

import java.time.LocalDateTime;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cbfacademy.frugalflyer.flights.airport.AirportInUseException;
import com.cbfacademy.frugalflyer.flights.airport.AirportNotFoundException;
import com.cbfacademy.frugalflyer.flights.flight.FlightNotFoundException;



/**
 * Class that handles exceptions for mulitple controllers across the application. 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Handler for AirportNotFoundException
     * @param e the exception that was thrown
     * @return a HTTP response containing a status and body
     */
    //Annotation tells Spring that anytime this exception is thrown in the program, route the error here.
    @ExceptionHandler(AirportNotFoundException.class)
    public ResponseEntity<ApiError> handleAirportNotFound(AirportNotFoundException e) {
        // Generate body of the Response Entity with the thrown error message, the HTTP Status and time of error.
        ApiError body = new ApiError(
            e.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now()
        );
        
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(body);
    }

    /**
     * Handler for InvalidClimateStringException
     * @param e the exception that was thrown
     * @return a HTTP response containing a status and body
     */
    //Annotation tells Spring that anytime this exception is thrown in the program, route the error here.
    @ExceptionHandler(InvalidClimateStringException.class)
    public ResponseEntity<ApiError> handleInvalidClimateString(InvalidClimateStringException e) {
        // Generate body of the Response Entity with the thrown error message, the HTTP Status and time of error.
        ApiError body = new ApiError(
            e.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now()
        );
        
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(body);
    }

    /**
     * Handler for InvalidTagStringException
     * @param e the exception that was thrown
     * @return a HTTP response containing a status and body
     */
    //Annotation tells Spring that anytime this exception is thrown in the program, route the error here.
    @ExceptionHandler(InvalidTagStringException.class)
    public ResponseEntity<ApiError> handleInvalidTagString(InvalidTagStringException e) {
        // Generate body of the Response Entity with the thrown error message, the HTTP Status and time of error.
        ApiError body = new ApiError(
            e.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now()
        );
        
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(body);
    }

    /**
     * Handler for InvalidDateException
     * @param e the exception that was thrown
     * @return a HTTP response containing a status and body
     */
    //Annotation tells Spring that anytime this exception is thrown in the program, route the error here.
    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ApiError> handleInvalidDate(InvalidDateException e) {
        // Generate body of the Response Entity with the thrown error message, the HTTP Status and time of error.
        ApiError body = new ApiError(
            e.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now()
        );
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
    }

    /**
     * Handler for InvalidNumberException
     * @param e the exception that was thrown
     * @return a HTTP response containing a status and body
     */
    //Annotation tells Spring that anytime this exception is thrown in the program, route the error here.
    @ExceptionHandler(InvalidNumberException.class)
    public ResponseEntity<ApiError> handleInvalidNumber(InvalidNumberException e) {
        // Generate body of the Response Entity with the thrown error message, the HTTP Status and time of error.
        ApiError body = new ApiError(
            e.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now()
        );
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
    }

    @ExceptionHandler(AirportInUseException.class)
    public ResponseEntity<ApiError> AirportInUse(AirportInUseException e) {
        // Generate body of the Response Entity with the thrown error message, the HTTP Status and time of error.
        ApiError body = new ApiError(
            e.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now()
        );
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> IllegalArgument(IllegalArgumentException e) {
        // Generate body of the Response Entity with the thrown error message, the HTTP Status and time of error.
        ApiError body = new ApiError(
            e.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now()
        );
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ApiError> OptimisticLockingFailure(OptimisticLockingFailureException e) {
        // Generate body of the Response Entity with the thrown error message, the HTTP Status and time of error.
        ApiError body = new ApiError(
            e.getMessage(),
            HttpStatus.PRECONDITION_FAILED.value(),
            LocalDateTime.now()
        );
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
    }

    /**
     * Handler for AirportNotFoundException
     * @param e the exception that was thrown
     * @return a HTTP response containing a status and body
     */
    //Annotation tells Spring that anytime this exception is thrown in the program, route the error here.
    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<ApiError> FlightNotFound(FlightNotFoundException e) {
        // Generate body of the Response Entity with the thrown error message, the HTTP Status and time of error.
        ApiError body = new ApiError(
            e.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now()
        );
        
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(body);
    }

}
