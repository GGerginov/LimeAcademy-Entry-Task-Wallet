package com.example.gwallet.model.validators;


import com.example.gwallet.model.anotations.AddressExist;
import com.example.gwallet.service.WalletService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressExistValidator implements ConstraintValidator<AddressExist,String> {

    private final WalletService walletService;

    @Autowired
    public AddressExistValidator(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public void initialize(AddressExist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String address, ConstraintValidatorContext context) {
        return this.walletService.isAddressExist(address);
    }

}
