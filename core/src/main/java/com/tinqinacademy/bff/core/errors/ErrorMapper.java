package com.tinqinacademy.bff.core.errors;


import com.tinqinacademy.bff.api.errors.Errors;

public interface ErrorMapper {
    Errors map(Throwable throwable);
}
