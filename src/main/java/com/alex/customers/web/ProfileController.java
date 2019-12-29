package com.alex.customers.web;

import com.alex.customers.model.UserUS;
import com.alex.customers.repo.StateUSRepo;
import com.alex.customers.repo.UserCANRepo;
import com.alex.customers.repo.UserUSRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/profile")
@SessionAttributes("user")
public class ProfileController {
    private UserUSRepo userUSRepo;
    private UserCANRepo userCANRepo;
    private StateUSRepo stateUSRepo;
    private PasswordEncoder encoder;

    public ProfileController(UserUSRepo userUSRepo, UserCANRepo userCANRepo, StateUSRepo stateUSRepo, PasswordEncoder encoder) {
        this.userUSRepo = userUSRepo;
        this.userCANRepo = userCANRepo;
        this.stateUSRepo = stateUSRepo;
        this.encoder = encoder;
    }

    @ModelAttribute
    public void addDataToModel(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserUS userUS = userUSRepo.findByUsername(principal.getUsername());

        if (userUS != null) {
            model.addAttribute("user", userUS);
        } else model.addAttribute("user", userCANRepo.findByUsername(principal.getUsername()));

        model.addAttribute("us", com.alex.customers.model.User.Country.USA);
        model.addAttribute("can", com.alex.customers.model.User.Country.CANADA);
    }

    @GetMapping
    public String showProfile() {
        return "profile";
    }
}
