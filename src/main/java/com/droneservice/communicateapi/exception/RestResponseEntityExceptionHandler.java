package com.droneservice.communicateapi.exception;

import com.droneservice.communicateapi.data.response.MessageResponse;
import com.droneservice.communicateapi.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DroneSrvRuntimeException.class})
    public ResponseEntity<MessageResponse> handleInvalidInputException(DroneSrvRuntimeException ex) {
        log.error("Invalid Input Exception: {}", ex.getMessage());
        return new ResponseEntity<>(new MessageResponse(Utils.FAILED, ex.getMessage(), java.time.LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<MessageResponse> handleInvalidArgumentException(IllegalArgumentException ex) {
        log.error("Invalid Input Exception: {}", ex.getMessage());
        return new ResponseEntity<>(new MessageResponse(Utils.FAILED, ex.getMessage(), java.time.LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }
}
