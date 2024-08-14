package com.tinqinacademy.bff.core.errors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.errors.ErrorInfo;
import com.tinqinacademy.bff.api.errors.Errors;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FeignErrorMapper {
    private final ObjectMapper objectMapper;

    public Errors mapFeignException(FeignException feignException) {
        List<ErrorInfo> errorDescription = new ArrayList<>();

        try {
            String jsonString = feignException.contentUTF8();
            List<HashMap<String, String>> errorMap = objectMapper.readValue(jsonString, List.class);
            errorDescription = errorMap.stream()
                    .map(error -> new ErrorInfo(error.get("error"), HttpStatus.valueOf(error.get("status"))))
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            errorDescription.add(new ErrorInfo(feignException.getMessage(), feignException.status() == -1 ?
                    HttpStatus.BAD_REQUEST :
                    HttpStatus.valueOf(feignException.status())));
        }


        return new Errors(errorDescription);
    }
}
