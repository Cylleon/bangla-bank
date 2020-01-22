package com.github.cylleon.banglabank.controllers;

import com.github.cylleon.banglabank.services.BankDepositService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class BankDepositController {

    private static final Logger log = LoggerFactory.getLogger(BankDepositController.class);

    private final BankDepositService bankDepositService;

    public BankDepositController(BankDepositService bankDepositService) {
        this.bankDepositService = bankDepositService;
    }
}
