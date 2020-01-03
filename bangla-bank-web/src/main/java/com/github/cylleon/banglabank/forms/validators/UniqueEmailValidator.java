package com.github.cylleon.banglabank.forms.validators;

import com.github.cylleon.banglabank.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null && userService.findUserByEmail(email) == null;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }
}
