package com.tinqinacademy.bff.api.validation.bathroomtype;


import com.tinqinacademy.bff.api.model.enums.BffBathroomType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ValidBathroomTypeValidator implements ConstraintValidator<ValidBathroomType, BffBathroomType> {
    @Override
    public void initialize(ValidBathroomType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BffBathroomType bathroomType, ConstraintValidatorContext context) {
        return !BffBathroomType.UNKNOWN.equals(bathroomType);
    }
}
