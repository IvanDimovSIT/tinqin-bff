package com.tinqinacademy.bff.api.validation.bedsize;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD,TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidBedSizeValidator.class)
public @interface ValidBedSize {
    String message() default "Invalid bed size";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
