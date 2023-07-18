package com.droneservice.communicateapi.exception;

import java.io.Serial;

public class DroneSrvRuntimeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DroneSrvRuntimeException(String message) {
        super(message);
    }
}
