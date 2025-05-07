package com.github.andercamargo.financial.infrastructure.common.validation.formats;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class NameValidator implements ConstraintValidator<Name, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null || name.isBlank()) return true; //ignore field in update
        return name.matches("^[^0-9]*$");
    }
}