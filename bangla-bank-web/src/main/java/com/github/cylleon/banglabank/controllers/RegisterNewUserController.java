package com.github.cylleon.banglabank.controllers;

import com.github.cylleon.banglabank.forms.UserForm;
import com.github.cylleon.banglabank.model.Authority;
import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.model.enums.AuthorityType;
import com.github.cylleon.banglabank.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegisterNewUserController {

    private static final Logger log = LoggerFactory.getLogger(RegisterNewUserController.class);

    private static final String REGISTER = "register";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterNewUserController(final UserService userService, final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerNewUser(Model model) {
        model.addAttribute("newUser", new UserForm());
        return REGISTER;
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("newUser") @Valid UserForm userForm, BindingResult result) {
        if (result.hasErrors()) {
            log.debug("User registration errors {}", result.getAllErrors());
            return REGISTER;
        }

        User newUser = User.builder()
              .email(userForm.getEmail())
              .password(passwordEncoder.encode(userForm.getPassword()))
              .balance(0.0)
              .authorities(Collections.singleton(new Authority(AuthorityType.ROLE_USER)))
              .build();
        if (userService.saveOrUpdateUser(newUser)) {
            return "redirect:/login";
        } else {
            return "error";
        }
    }
}
