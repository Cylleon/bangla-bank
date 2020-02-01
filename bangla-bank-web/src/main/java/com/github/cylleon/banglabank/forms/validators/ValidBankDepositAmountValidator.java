package com.github.cylleon.banglabank.forms.validators;

import com.github.cylleon.banglabank.model.BankDeposit;
import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.services.BankDepositService;
import com.github.cylleon.banglabank.services.UserService;
import com.github.cylleon.banglabank.services.model.BankUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ValidBankDepositAmountValidator implements ConstraintValidator<ValidBankDepositAmount, Double> {

    private final Authentication authentication;
    private final UserService userService;
    private final BankDepositService bankDepositService;

    public ValidBankDepositAmountValidator(UserService userService, BankDepositService bankDepositService) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        this.userService = userService;
        this.bankDepositService = bankDepositService;
    }

    @Override
    public boolean isValid(Double amount, ConstraintValidatorContext constraintValidatorContext) {
        if (authentication.getPrincipal() == null) {
            return false;
        }
        if (amount < 0) {
            return false;
        }
        User user = userService.findUserByEmail(((BankUserDetails)authentication.getPrincipal()).getUser().getEmail());
        Optional<BankDeposit> bankDeposit = bankDepositService.getBankDepositByUserId(user.getId());
        if (bankDeposit.isPresent()) {
            Double depositedAmount = amount - bankDeposit.get().getAmount();
            if (depositedAmount < 0) {
                return true;
            } else {
                return user.getBalance() >= depositedAmount;
            }
        } else {
            return user.getBalance() >= amount;
        }
    }

    @Override
    public void initialize(ValidBankDepositAmount constraintAnnotation) {

    }
}
