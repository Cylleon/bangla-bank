package com.github.cylleon.banglabank.controllers;

import com.github.cylleon.banglabank.common.TransactionForm;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;

@Controller
public class SendTransactionController {

    private static final Logger log = LoggerFactory.getLogger(SendTransactionController.class);

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
        model.addAttribute("error", false);
        return "sendTransaction";
    }

    @PostMapping("/sendTransaction")
    public String sendTransactionToRecipient(Model model, @ModelAttribute TransactionForm transactionForm,
                                             Authentication authentication) {
        User loggedUser = ((BankUserDetails)authentication.getPrincipal()).getUser();
        if (loggedUser.getBalance() < transactionForm.amount) {
            log.debug("User balance is too low to make this transaction. Balance {}, amount {}",
                  loggedUser.getBalance(), transactionForm.amount);
            model.addAttribute("error", true);
            return "sendTransaction";
        }
        User recipient = userService.findUserByEmail(transactionForm.to);
        if (recipient == null) {
            log.debug("User with {} email not found", transactionForm.to);
            model.addAttribute("error", true);
            return "sendTransaction";
        }
        Transaction transaction = Transaction.builder()
              .sender(loggedUser)
              .recipient(recipient)
              .amount(transactionForm.amount)
              .timestamp(Instant.now())
              .build();
        transactionService.makeTransaction(transaction);
        return "sendTransaction";
    }
}
