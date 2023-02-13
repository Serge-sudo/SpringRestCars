package com.automobile.machineStorage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IllegalParameterException extends RuntimeException {
    public IllegalParameterException(String message) {
        super(message);
    }
}