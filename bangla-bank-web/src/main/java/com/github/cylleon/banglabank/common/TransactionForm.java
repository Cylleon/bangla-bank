package com.github.cylleon.banglabank.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionForm {
    public String to;
    public Double amount;

    public TransactionForm() {
    }
}
