package com.github.cylleon.banglabank.controllers;

import com.github.cylleon.banglabank.forms.PasswordResetForm;
import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ResetPasswordController {

    private static final Logger log = LoggerFactory.getLogger(ResetPasswordController.class);

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public ResetPasswordController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/passwordReset/{id}")
    public String passwordReset(@PathVariable String id, Model model) {
        userService.findById(Integer.valueOf(id)).ifPresent(user -> model.addAttribute("user", user));
        model.addAttribute("passwordResetForm", new PasswordResetForm());
        return "passwordReset";
    }

    @PostMapping("/passwordReset/{id}")
    public String passwordReset(@PathVariable String id,
                                @ModelAttribute("passwordResetForm") @Valid PasswordResetForm passwordResetForm,
                                BindingResult result) {
        if (result.hasErrors()) {
            log.debug("Sending transaction errors {}", result.getAllErrors());
            return "redirect:/passwordReset/" + id;
        }
        Optional<User> user = userService.findById(Integer.valueOf(id));
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(passwordResetForm.getPassword()));
            if (userService.saveOrUpdateUser(user.get())) {
                return "redirect:/";
            } else {
                return "error";
            }
        } else {
            return "error";
        }
    }
}
