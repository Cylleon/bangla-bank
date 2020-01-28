package com.github.cylleon.banglabank.controllers;

import com.github.cylleon.banglabank.forms.BankDepositForm;
import com.github.cylleon.banglabank.model.BankDeposit;
import com.github.cylleon.banglabank.model.DailyInterest;
import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.services.BankDepositService;
import com.github.cylleon.banglabank.services.UserService;
import com.github.cylleon.banglabank.services.model.BankUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Optional;

@Controller
public class BankDepositController {

    private static final Logger log = LoggerFactory.getLogger(BankDepositController.class);

    private final BankDepositService bankDepositService;
    private final UserService userService;

    public BankDepositController(BankDepositService bankDepositService, UserService userService) {
        this.bankDepositService = bankDepositService;
        this.userService = userService;
    }

    @GetMapping("/bankDeposit")
    public String bankDeposit(Model model, Authentication authentication) {
        User user = ((BankUserDetails) authentication.getPrincipal()).getUser();
        Optional<BankDeposit> bankDeposit = bankDepositService.getBankDepositByUserId(user.getId());
        Double amountOfDeposit;
        if (bankDeposit.isPresent()) {
            amountOfDeposit = bankDeposit.get().getAmount();
        } else {
            amountOfDeposit = 0.0;
        }
        model.addAttribute("bankDeposit", new BankDepositForm(amountOfDeposit));
        model.addAttribute("dailyInterest", bankDepositService.getDailyInterestByUserId(user.getId()));
        return "bankDeposit";
    }

    @PostMapping("/bankDeposit")
    public ModelAndView newBankDeposit(@ModelAttribute("bankDeposit") @Valid BankDepositForm bankDepositForm,
                                       BindingResult result, Authentication authentication) {
        User user = ((BankUserDetails) authentication.getPrincipal()).getUser();
        ModelAndView modelAndView = new ModelAndView("bankDeposit");
        modelAndView.addObject("bankDeposit", bankDepositForm);
        if (result.hasErrors()) {
            log.debug("Creating new bank deposit errors {}", result.getAllErrors());
            return modelAndView;
        }
        Optional<BankDeposit> bankDeposit = bankDepositService.getBankDepositByUserId(user.getId());
        DailyInterest dailyInterest = DailyInterest.builder()
              .timestamp(Instant.now())
              .build();
        if (bankDeposit.isPresent()) {
            Double depositedAmount = bankDepositForm.getAmount() - bankDeposit.get().getAmount();
            bankDeposit.get().setAmount(bankDeposit.get().getAmount() + depositedAmount);
            user.setBalance(user.getBalance() - depositedAmount);
            dailyInterest.setInterest(depositedAmount);
        } else {
            bankDeposit = Optional.of(BankDeposit.builder()
                  .amount(bankDepositForm.getAmount())
                  .user(user)
                  .build());
            dailyInterest.setInterest(bankDepositForm.getAmount());
            user.setBalance(user.getBalance() - bankDepositForm.getAmount());
        }
        dailyInterest.setBankDeposit(bankDeposit.get());
        if (bankDepositService.saveBankDeposit(bankDeposit.get())
              && bankDepositService.saveDailyInterest(dailyInterest) && userService.saveOrUpdateUser(user)) {
            modelAndView.addObject("dailyInterest", bankDepositService.getDailyInterestByUserId(user.getId()));
            return modelAndView;
        } else  {
            return new ModelAndView("error");
        }

    }
}
