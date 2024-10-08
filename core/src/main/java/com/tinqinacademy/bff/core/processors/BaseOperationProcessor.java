package com.tinqinacademy.bff.core.processors;



import com.tinqinacademy.bff.api.base.OperationInput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.api.exception.exceptions.ViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public abstract class BaseOperationProcessor {
    protected final ConversionService conversionService;
    protected final ErrorMapper errorMapper;
    private final Validator validator;

    protected <T extends OperationInput> void validate(T input) {
        Set<ConstraintViolation<T>> set = validator.validate(input);
        if (!set.isEmpty()) {
            List<String> error = set.stream()
                    .map(violation -> String.format("%s : %s", violation.getPropertyPath(), violation.getMessage()))
                    .toList();

            throw new ViolationException(error);
        }
    }
}
