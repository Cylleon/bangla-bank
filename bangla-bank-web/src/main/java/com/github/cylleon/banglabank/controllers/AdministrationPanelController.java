package com.github.cylleon.banglabank.controllers;

import com.github.cylleon.banglabank.model.User;
import com.github.cylleon.banglabank.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@Controller
public class AdministrationPanelController {

    private static final Logger log = LoggerFactory.getLogger(AdministrationPanelController.class);

    private static final String ADMINISTRATION_PANEL = "administrationPanel";

    private final UserService userService;

    @Autowired
    public AdministrationPanelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/administrationPanel")
    public String administrationPanel(Model model) {
        model.addAttribute("users", userService.findAll());
        return ADMINISTRATION_PANEL;
    }

    @GetMapping("/administrationPanel/deactivate/{id}")
    public String deactivatedUser(@PathVariable("id") int id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            user.get().setActive(false);
            log.info("Deactivated user with id - {}", id);
            userService.saveOrUpdateUser(user.get());
        } else {
            log.error("User with id - {} doesn't not exist", id);
        }
        return "redirect:/" + ADMINISTRATION_PANEL;
    }

    @GetMapping("/administrationPanel/activate/{id}")
    public String activateUser(@PathVariable("id") int id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            user.get().setActive(true);
            log.info("Activated user with id - {}", id);
            userService.saveOrUpdateUser(user.get());
        } else {
            log.error("User with id - {} doesn't not exist", id);
        }
        return "redirect:/" + ADMINISTRATION_PANEL;
    }
}
