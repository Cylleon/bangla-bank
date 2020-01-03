package com.github.cylleon.banglabank.controllers;

import com.github.cylleon.banglabank.forms.TransactionForm;
import com.github.cylleon.banglabank.model.Transaction;
import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.services.TransactionService;
import com.github.cylleon.banglabank.services.UserService;
import com.github.cylleon.banglabank.services.model.BankUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.Instant;

@Controller
public class SendTransactionController {

    private static final Logger log = LoggerFactory.getLogger(SendTransactionController.class);

    private static final String SEND_TRANSACTION = "sendTransaction";

    private final TransactionService transactionService;
    private final UserService userService;

    @Autowired
    public SendTransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/sendTransaction")
    public String sendTransaction(Model model) {
        model.addAttribute("transactionForm", new TransactionForm());
        return SEND_TRANSACTION;
    }

    @PostMapping("/sendTransaction")
    public String sendTransactionToRecipient(@ModelAttribute("transactionForm") @Valid TransactionForm transactionForm,
                                             BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            log.debug("Sending transaction errors {}", result.getAllErrors());
            return SEND_TRANSACTION;
        }
        User loggedUser = ((BankUserDetails)authentication.getPrincipal()).getUser();
        User recipient = userService.findUserByEmail(transactionForm.getRecipient());
        Transaction transaction = Transaction.builder()
              .sender(loggedUser)
              .recipient(recipient)
              .amount(transactionForm.getAmount())
              .timestamp(Instant.now())
              .build();
        transactionService.makeTransaction(transaction);
        return SEND_TRANSACTION;
    }
}
