package com.github.cylleon.banglabank.services;

import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void updateUserBalance(User user, Double balanceToAdd) {
        user.setBalance(user.getBalance() + balanceToAdd);
        userRepository.saveAndFlush(user);
    }

    public void registerNewUser(User newUser) {
        userRepository.saveAndFlush(newUser);
    }
}
