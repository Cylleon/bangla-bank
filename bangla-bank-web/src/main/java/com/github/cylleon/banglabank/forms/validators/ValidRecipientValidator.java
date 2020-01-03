package com.github.cylleon.banglabank.forms.validators;

import com.github.cylleon.banglabank.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidRecipientValidator implements ConstraintValidator<ValidRecipient, String> {

    private final UserService userService;

    public ValidRecipientValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userService.findUserByEmail(email) != null;
    }

    @Override
    public void initialize(ValidRecipient constraintAnnotation) {

    }

}