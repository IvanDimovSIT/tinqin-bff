package com.tinqinacademy.bff.api.validation.bedsize;


import com.tinqinacademy.bff.api.model.enums.BffBedSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ValidBedSizeValidator implements ConstraintValidator<ValidBedSize, BffBedSize> {
    @Override
    public void initialize(ValidBedSize constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BffBedSize bedSize, ConstraintValidatorContext context) {
        return !BffBedSize.UNKNOWN.equals(bedSize);
    }
}
