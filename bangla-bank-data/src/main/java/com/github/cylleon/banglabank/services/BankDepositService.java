package com.github.cylleon.banglabank.services;

import com.github.cylleon.banglabank.model.BankDeposit;
import com.github.cylleon.banglabank.model.DailyInterest;
import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.repositories.BankDepositRepository;
import com.github.cylleon.banglabank.repositories.DailyInterestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BankDepositService {

    private static final Logger log = LoggerFactory.getLogger(BankDepositService.class);

    private static final double DAILY_INTEREST_RATE = 0.02;

    private final BankDepositRepository bankDepositRepository;
    private final DailyInterestRepository dailyInterestRepository;
    private final UserService userService;

    @Autowired
    public BankDepositService(BankDepositRepository bankDepositRepository,
                              DailyInterestRepository dailyInterestRepository, UserService userService) {
        this.bankDepositRepository = bankDepositRepository;
        this.dailyInterestRepository = dailyInterestRepository;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 */24 * * *")
    public void calculateDailyInterest() {
        List<BankDeposit> updatedDeposit = new ArrayList<>();
        List<DailyInterest> dailyInterests = new ArrayList<>();
        bankDepositRepository.findAll().stream()
              .filter(bankDeposit -> {
                  Optional<User> user = userService.findById(bankDeposit.getId());
                  return user.isPresent() && user.get().isActive();
              })
              .forEach(bankDeposit -> {
                  double interest = Math.round((bankDeposit.getAmount() * DAILY_INTEREST_RATE) * 100) / 100.0;
                  DailyInterest dailyInterest = DailyInterest.builder()
                        .bankDeposit(bankDeposit)
                        .interest(interest)
                        .timestamp(Instant.now())
                        .build();
                  bankDeposit.setAmount(bankDeposit.getAmount() + dailyInterest.getInterest());
                  log.debug("Updated bank deposit {} by amount {}", bankDeposit, dailyInterest);
                  updatedDeposit.add(bankDeposit);
                  dailyInterests.add(dailyInterest);
              });
        bankDepositRepository.saveAll(updatedDeposit);
        bankDepositRepository.flush();
        dailyInterestRepository.saveAll(dailyInterests);
        dailyInterestRepository.flush();
    }

    public Optional<BankDeposit> getBankDepositByUserId(int userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            return bankDepositRepository.findByUser(user.get());
        } else {
            return Optional.empty();
        }
    }

    public boolean saveBankDeposit(BankDeposit bankDeposit) {
        return bankDepositRepository.saveAndFlush(bankDeposit) != null;
    }

    public boolean saveDailyInterest(DailyInterest dailyInterest) {
        return dailyInterestRepository.saveAndFlush(dailyInterest) != null;
    }

    public List<DailyInterest> getDailyInterestByUserId(int userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            Optional<BankDeposit> bankDeposit = bankDepositRepository.findByUser(user.get());
            if (bankDeposit.isPresent()) {
                return dailyInterestRepository.findAllByBankDeposit(bankDeposit.get());
            }
        }
        return Collections.emptyList();
    }

}
