package com.example.gwallet.model.anotations;

import com.example.gwallet.model.validators.AddressExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {AddressExistValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AddressExist {
    String message() default "Address is not exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
