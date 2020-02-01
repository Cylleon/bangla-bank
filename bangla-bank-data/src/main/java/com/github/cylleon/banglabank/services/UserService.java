package com.github.cylleon.banglabank.services;

import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void updateUserBalance(User user, Double balanceToAdd) {
        user.setBalance(Math.round((user.getBalance() + balanceToAdd) * 100) / 100.0);
        userRepository.saveAndFlush(user);
    }

    public boolean saveOrUpdateUser(User user) {
        return userRepository.saveAndFlush(user) != null;
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

}
