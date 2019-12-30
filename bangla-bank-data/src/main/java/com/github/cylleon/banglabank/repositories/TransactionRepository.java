package com.github.cylleon.banglabank.repositories;

import com.github.cylleon.banglabank.model.Transaction;
import com.github.cylleon.banglabank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllBySender(User sender);
    List<Transaction> findAllByRecipient(User recipient);
}
