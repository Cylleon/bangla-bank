package com.github.cylleon.banglabank.forms;

import com.github.cylleon.banglabank.forms.validators.ValidAmount;
import com.github.cylleon.banglabank.forms.validators.ValidRecipient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionForm {
    @ValidRecipient
    private String recipient;
    @ValidAmount
    private Double amount;

    public TransactionForm() {
    }
}
