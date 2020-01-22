package com.github.cylleon.banglabank.services;

import com.github.cylleon.banglabank.model.BankDeposit;
import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.repositories.BankDepositRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankDepositService {

    private static final Logger log = LoggerFactory.getLogger(BankDepositService.class);

    private static final double DAILY_INTEREST_RATE = 0.02;

    private final BankDepositRepository bankDepositRepository;
    private final UserService userService;

    @Autowired
    public BankDepositService(BankDepositRepository bankDepositRepository, UserService userService) {
        this.bankDepositRepository = bankDepositRepository;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void calculateDailyInterest() {
        List<BankDeposit> updatedDeposit = new ArrayList<>();
        bankDepositRepository.findAll().forEach(bankDeposit -> {
            double dailyInterest = Math.round((bankDeposit.getAmount() * DAILY_INTEREST_RATE) * 100) / 100.0;
            bankDeposit.setAmount(bankDeposit.getAmount() + dailyInterest);
            log.debug("Updated bank deposit {} by amount {}", bankDeposit, dailyInterest);
            updatedDeposit.add(bankDeposit);
        });
        bankDepositRepository.saveAll(updatedDeposit);
    }

    public Optional<BankDeposit> getBankDepositByUserId(int userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            return bankDepositRepository.findByUser(user.get());
        } else {
            return Optional.empty();
        }
    }
}
