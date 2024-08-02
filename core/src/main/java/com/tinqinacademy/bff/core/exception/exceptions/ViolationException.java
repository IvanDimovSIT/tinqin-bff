package com.tinqinacademy.bff.core.exception.exceptions;



import lombok.Getter;

import java.util.List;

@Getter
public class ViolationException extends RuntimeException {
    private final List<String> errors;

    public ViolationException(List<String> errors) {
        this.errors = errors;
    }
}
