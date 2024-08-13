package com.tinqinacademy.bff.core.errors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.errors.Errors;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class FeignErrorMapper {
    private final ObjectMapper objectMapper;

    public Errors mapFeignException(FeignException feignException) {
        String errorDescription;

        try {
            String message = feignException.getMessage();
            int startIndex = message.indexOf("[[{");
            int endIndex = message.indexOf("}]]");

            if (startIndex != -1 && endIndex != -1) {
                String jsonString = message.substring(startIndex + 2, endIndex + 1);
                HashMap<String, String> errorMap = objectMapper.readValue(jsonString, HashMap.class);
                errorDescription = errorMap.get("error");
            } else {
                errorDescription = feignException.getMessage();
            }
        } catch (IOException exception) {
            errorDescription = feignException.getMessage();
        }

        return Errors.builder()
                .error(errorDescription, feignException.status() == -1 ?
                        HttpStatus.BAD_REQUEST :
                        HttpStatus.valueOf(feignException.status()))
                .build();
    }
}
