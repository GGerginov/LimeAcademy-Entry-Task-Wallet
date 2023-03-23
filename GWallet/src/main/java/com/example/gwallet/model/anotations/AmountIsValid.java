package com.example.gwallet.model.anotations;

import com.example.gwallet.model.validators.AddressExistValidator;
import com.example.gwallet.model.validators.AmountIsValidValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {AmountIsValidValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AmountIsValid {
    String message() default "Amount can not be negative or zero !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
