package com.github.cylleon.banglabank.forms;

import com.github.cylleon.banglabank.forms.validators.FieldsValueMatch;
import com.github.cylleon.banglabank.forms.validators.UniqueEmail;
import com.github.cylleon.banglabank.forms.validators.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FieldsValueMatch.List({
      @FieldsValueMatch(
            field = "password",
            fieldMatch = "passwordConfirm",
            message = "Passwords don't match!"
      )
})
public class UserForm {
    @UniqueEmail
    private String email;
    @ValidPassword
    private String password;
    private String passwordConfirm;

    public UserForm() {
    }
}
