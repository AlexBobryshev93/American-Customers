package com.alex.customers.web;

import com.alex.customers.model.User;
import com.alex.customers.model.UserCAN;
import com.alex.customers.model.UserUS;
import com.alex.customers.repo.StateUSRepo;
import com.alex.customers.repo.UserCANRepo;
import com.alex.customers.repo.UserUSRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/profile")
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
        model.addAttribute("us", User.Country.USA);
        model.addAttribute("can", User.Country.CANADA);
        model.addAttribute("statesList", stateUSRepo.findAll());
        model.addAttribute("provincesList", UserCAN.Province.values());
    }

    @GetMapping
    public String showProfile(Model model) {
        UserUS userUS = userUSRepo.findByUsername(getCurrentUserUsername());

        if (userUS != null) {
            model.addAttribute("user", userUS);
        } else model.addAttribute("user", userCANRepo.findByUsername(getCurrentUserUsername()));

        return "profile";
    }

    @GetMapping("/delete")
    @Transactional
    public String deleteProfile() {
        String username = getCurrentUserUsername();

        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        if(userUSRepo.deleteByUsername(username) != 1) userCANRepo.deleteByUsername(username);

        return "redirect:/login";
    }

    @GetMapping("/edit")
    public String editCountry() {
        return "edit_country";
    }

    @GetMapping("/edit/{country}")
    public String editUser(@PathVariable("country") String country, Model model) {
        UserUS userUS = userUSRepo.findByUsername(getCurrentUserUsername());

        if (userUS != null) {
            if (country.equals("us")) model.addAttribute("user", new UserUS(userUS));
            else model.addAttribute("user", new UserCAN(userUS));
        } else {
            UserCAN userCAN = userCANRepo.findByUsername(getCurrentUserUsername());
            if (country.equals("us")) model.addAttribute("user", new UserUS(userCAN));
            else model.addAttribute("user", new UserCAN(userCAN));
        }

        return "edit_user";
    }

    @PostMapping("/edit/us")
    @Transactional
    public String updateUserUS(@ModelAttribute UserUS user, Model model) {
        String editedUserUsername = getCurrentUserUsername();
        UserUS userUS = userUSRepo.findByUsername(user.getUsername());
        UserCAN userCAN = userCANRepo.findByUsername(user.getUsername());

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("errMsg", "Error: check your password and try again");
            if (userUS != null) model.addAttribute("user", new UserUS(userUS));
            else model.addAttribute("user", new UserUS(userCAN));
            return "edit_user";
        }

        // same names but different IDs means error
        if (((userUS != null) || (userCAN != null)) && !user.getUsername().equals(editedUserUsername)) {
            model.addAttribute("errMsg", "Error: user with such name already exists");
            if (userUS != null) model.addAttribute("user", new UserUS(userUS));
            else model.addAttribute("user", new UserUS(userCAN));
            return "edit_user";
        }

        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        userUSRepo.deleteByUsername(editedUserUsername);
        userCANRepo.deleteByUsername(editedUserUsername);

        user.setPassword(encoder.encode(user.getPassword()));
        user.setState(stateUSRepo.findByName(user.getStateName()));
        userUSRepo.save(user);

        return "redirect:/login";
    }

    @PostMapping("/edit/can")
    @Transactional
    public String updateUserCAN(@ModelAttribute UserCAN user, Model model) {
        String editedUserUsername = getCurrentUserUsername();
        UserUS userUS = userUSRepo.findByUsername(user.getUsername());
        UserCAN userCAN = userCANRepo.findByUsername(user.getUsername());

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("errMsg", "Error: check your password and try again");
            if (userUS != null) model.addAttribute("user", new UserCAN(userUS));
            else model.addAttribute("user", new UserCAN(userCAN));
            return "edit_user";
        }

        // same names but different IDs means error
        if (((userUS != null) || (userCAN != null)) && !user.getUsername().equals(editedUserUsername)) {
            model.addAttribute("errMsg", "Error: user with such name already exists");
            if (userUS != null) model.addAttribute("user", new UserCAN(userUS));
            else model.addAttribute("user", new UserCAN(userCAN));
            return "edit_user";
        }

        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        userUSRepo.deleteByUsername(editedUserUsername);
        userCANRepo.deleteByUsername(editedUserUsername);

        user.setPassword(encoder.encode(user.getPassword()));
        userCANRepo.save(user);

        return "redirect:/login";
    }

    private String getCurrentUserUsername() {
        return ((org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUsername();
    }
}
