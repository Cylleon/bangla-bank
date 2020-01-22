package com.github.cylleon.banglabank.controllers;

import com.github.cylleon.banglabank.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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

    @GetMapping("/administrationPanel/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        log.info("Deleted user with id - {}", id);
        return "redirect:/" + ADMINISTRATION_PANEL;
    }
}
