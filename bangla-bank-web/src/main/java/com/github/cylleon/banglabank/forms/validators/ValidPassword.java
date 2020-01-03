package com.github.cylleon.banglabank.forms.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPasswordValidator.class)
public @interface ValidPassword {
    String message() default "At least 8 characters, must contain at least 1 uppercase letter," +
          " 1 lowercase latter and 1 number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
