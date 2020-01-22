package com.github.cylleon.banglabank.bootstrap;

import com.github.cylleon.banglabank.model.Authority;
import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.model.enums.AuthorityType;
import com.github.cylleon.banglabank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DevBootstrap(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {

        User user = new User();
        user.setEmail("abc@gmail.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setBalance(100.0);
        user.setAuthorities(Collections.singleton(new Authority(AuthorityType.ROLE_USER)));
        userRepository.save(user);

        User user1 = new User();
        user1.setEmail("abcd@gmail.com");
        user1.setPassword(passwordEncoder.encode("1q2w3e"));
        user1.setBalance(1.0);
        user1.setAuthorities(Collections.singleton(new Authority(AuthorityType.ROLE_USER)));
        userRepository.save(user1);

        User admin = new User();
        admin.setEmail("xyz@xyz.com");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setBalance(0.0);
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(new Authority(AuthorityType.ROLE_USER));
        authoritySet.add(new Authority(AuthorityType.ROLE_ADMIN));
        admin.setAuthorities(authoritySet);
        userRepository.saveAndFlush(admin);
    }
}
