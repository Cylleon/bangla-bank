package com.github.cylleon.banglabank.forms;

import com.github.cylleon.banglabank.forms.validators.ValidBankDepositAmount;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BankDepositForm {
    @NotNull
    @ValidBankDepositAmount
    private Double amount;

    public BankDepositForm() {
    }

    public BankDepositForm(@NotNull Double amount) {
        this.amount = amount;
    }
}
