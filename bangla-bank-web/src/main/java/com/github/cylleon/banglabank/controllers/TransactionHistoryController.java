package com.github.cylleon.banglabank.controllers;

import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.services.TransactionService;
import com.github.cylleon.banglabank.services.model.BankUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionHistoryController {

    private static final Logger log = LoggerFactory.getLogger(TransactionHistoryController.class);

    private final TransactionService transactionService;

    @Autowired
    public TransactionHistoryController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactionHistory")
    public String transactionHistory(Model model, Authentication authentication) {
        User loggedUser = ((BankUserDetails) authentication.getPrincipal()).getUser();
        model.addAttribute("sentTransactions", transactionService.getListOfSentTransactionByUser(loggedUser));
        model.addAttribute("receivedTransactions", transactionService.getListOfReceivedTransactionByUser(loggedUser));
        return "transactionHistory";
    }
}
