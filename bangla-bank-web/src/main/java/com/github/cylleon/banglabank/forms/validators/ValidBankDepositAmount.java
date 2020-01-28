package com.github.cylleon.banglabank.forms.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidBankDepositAmountValidator.class)
public @interface ValidBankDepositAmount {
    String message() default "Insufficiently balance";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
