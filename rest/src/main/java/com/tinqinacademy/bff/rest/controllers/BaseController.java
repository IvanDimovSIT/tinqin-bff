package com.tinqinacademy.bff.rest.controllers;


import com.tinqinacademy.bff.api.base.OperationOutput;
import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.core.security.JwtToken;
import com.tinqinacademy.bff.core.security.JwtUtil;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public abstract class BaseController {
    protected JwtUtil jwtUtil;

    protected <T extends OperationOutput> ResponseEntity<?> mapToResponseEntity(Either<Errors, T> either, HttpStatus status) {
        return either.isRight()?
                new ResponseEntity<>(either.get(), status):
                new ResponseEntity<>(either.getLeft().getErrorInfos(), either.getLeft().getStatus());
    }

    protected String extractUserIdFromToken(String jwtHeader) {
        return jwtUtil.decodeHeader(jwtHeader).getId();
    }
}
