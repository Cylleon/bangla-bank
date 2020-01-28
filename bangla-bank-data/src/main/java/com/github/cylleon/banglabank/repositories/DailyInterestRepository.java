package com.github.cylleon.banglabank.repositories;

import com.github.cylleon.banglabank.model.BankDeposit;
import com.github.cylleon.banglabank.model.DailyInterest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyInterestRepository extends JpaRepository<DailyInterest, Integer> {
    List<DailyInterest> findAllByBankDeposit(BankDeposit bankDeposit);

}
