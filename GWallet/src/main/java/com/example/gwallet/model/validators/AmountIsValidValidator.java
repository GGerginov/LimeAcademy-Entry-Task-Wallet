package com.example.gwallet.model.validators;

import com.example.gwallet.model.anotations.AddressExist;
import com.example.gwallet.model.anotations.AmountIsValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AmountIsValidValidator implements ConstraintValidator<AmountIsValid,Double> {
    @Override
    public void initialize(AmountIsValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double amount, ConstraintValidatorContext context) {
        return amount > 0;
    }
}
