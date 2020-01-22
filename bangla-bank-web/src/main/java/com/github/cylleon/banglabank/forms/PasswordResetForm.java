package com.github.cylleon.banglabank.forms;

import com.github.cylleon.banglabank.forms.validators.FieldsValueMatch;
import com.github.cylleon.banglabank.forms.validators.ValidPassword;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@FieldsValueMatch.List({
      @FieldsValueMatch(
            field = "password",
            fieldMatch = "passwordConfirm",
            message = "Passwords don't match!"
      )
})
public class PasswordResetForm {

    @ValidPassword
    private String password;
    private String passwordConfirm;

    public PasswordResetForm() {
    }
}
