package com.github.cylleon.banglabank.services;

import com.github.cylleon.banglabank.model.Transaction;
import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }


    public List<Transaction> getListOfSentTransactionByUser(User sender) {
        return transactionRepository.findAllBySender(sender);
    }

    public List<Transaction> getListOfReceivedTransactionByUser(User recipient) {
        return transactionRepository.findAllByRecipient(recipient);
    }

    public void makeTransaction(Transaction transaction) {
        userService.updateUserBalance(transaction.getSender(), (transaction.getAmount() * -1.0));
        userService.updateUserBalance(transaction.getRecipient(), transaction.getAmount());
        transactionRepository.save(transaction);
    }


}
