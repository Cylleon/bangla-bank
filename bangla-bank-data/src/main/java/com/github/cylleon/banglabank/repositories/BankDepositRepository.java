package com.github.cylleon.banglabank.repositories;

import com.github.cylleon.banglabank.model.BankDeposit;
import com.github.cylleon.banglabank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankDepositRepository extends JpaRepository<BankDeposit, Integer> {
    Optional<BankDeposit> findByUser(User user);
}
