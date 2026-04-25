package com.internship.tool.exception;

import com.internship.tool.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                404,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(InvalidDataException ex) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                400,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleServerError(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                500,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}