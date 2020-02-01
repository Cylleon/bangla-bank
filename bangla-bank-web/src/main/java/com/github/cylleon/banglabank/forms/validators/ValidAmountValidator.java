package com.github.cylleon.banglabank.forms.validators;

import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.services.UserService;
import com.github.cylleon.banglabank.services.model.BankUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidAmountValidator implements ConstraintValidator<ValidAmount, Double> {

    private final Authentication authentication;
    private final UserService userService;

    public ValidAmountValidator(UserService userService) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        this.userService = userService;
    }

    @Override
    public boolean isValid(Double amount, ConstraintValidatorContext constraintValidatorContext) {
        if (authentication.getPrincipal() == null) {
            return false;
        }
        User user = userService.findUserByEmail(((BankUserDetails)authentication.getPrincipal()).getUser().getEmail());
        return user.getBalance() >= amount && amount > 0;
    }

    @Override
    public void initialize(ValidAmount constraintAnnotation) {

    }
}
