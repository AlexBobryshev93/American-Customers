package com.alex.customers.web;

import com.alex.customers.model.UserCAN;
import com.alex.customers.model.UserUS;
import com.alex.customers.repo.StateUSRepo;
import com.alex.customers.repo.UserCANRepo;
import com.alex.customers.repo.UserUSRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserUS userUS = userUSRepo.findByUsername(principal.getUsername());

        if (userUS != null) {
            model.addAttribute("user", userUS);
        } else model.addAttribute("user", userCANRepo.findByUsername(principal.getUsername()));

        model.addAttribute("us", com.alex.customers.model.User.Country.USA);
        model.addAttribute("can", com.alex.customers.model.User.Country.CANADA);
        model.addAttribute("statesList", stateUSRepo.findAll());
        model.addAttribute("provincesList", UserCAN.Province.values());
        model.addAttribute("principal", principal);
    }

    @GetMapping
    public String showProfile() {
        return "profile";
    }

    @GetMapping("/delete")
    @Transactional
    public String deleteProfile(@ModelAttribute User principal) {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        if(userUSRepo.deleteByUsername(principal.getUsername()) != 1) userCANRepo.deleteByUsername(principal.getUsername());

        return "redirect:/login";
    }

    @GetMapping("/edit")
    public String editCountry() {
        return "edit_country";
    }
/*
    @GetMapping("/edit/{country}")
    public String editUser(@PathVariable("country") String country, @ModelAttribute User principal, Model model) {
        UserUS userUS = userUSRepo.findByUsername(principal.getUsername());
        //System.out.println("REACHED2................................");

        if (userUS != null) {
            if (country.equals("us")) model.addAttribute("user", new UserUS(userUS));
            else model.addAttribute("user", new UserCAN(userUS));
            //System.out.println(userUS);
        } else {
            UserCAN userCAN = userCANRepo.findByUsername(principal.getUsername());
            if (country.equals("us")) model.addAttribute("user", new UserUS(userCAN));
            else model.addAttribute("user", new UserCAN(userCAN));
            //System.out.println(userCAN);
        }

        return "edit_user";
    } */

/*
    @GetMapping("/edit/us")
    public String editUser(@ModelAttribute User principal, Model model) {
        UserUS userUS = userUSRepo.findByUsername(principal.getUsername());

        if (userUS != null) model.addAttribute("user", new UserUS(userUS));
        else model.addAttribute("user", new UserUS(userCANRepo.findByUsername(principal.getUsername())));

        return "edit_user";
    }
*/

    @GetMapping("/edit/us")
    public String editUser(@ModelAttribute User principal, Model model) {
        UserUS userUS = userUSRepo.findByUsername(principal.getUsername());

        if (userUS != null) model.addAttribute("user", new UserUS(userUS));
        else model.addAttribute("user", new UserUS(userCANRepo.findByUsername(principal.getUsername())));

        return "edit_user";
    }

    @PostMapping("/edit/us")
    public String updateUserUS(@ModelAttribute UserUS user, Model model) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("errMsg", "Error: check your password and try again");
            return "edit_user";
        }

        if (userUSRepo.findByUsername(user.getUsername()) != null || userCANRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("errMsg", "Error: user with such name already exists");
            return "edit_user";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setState(stateUSRepo.findByName(user.getStateName()));
        userUSRepo.save(user);

        return "redirect:/login";
    }

    @PostMapping("/edit/can")
    public String updateUserCAN(@ModelAttribute UserCAN user, Model model) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("errMsg", "Error: check your password and try again");
            return "edit_user";
        }

        if (userUSRepo.findByUsername(user.getUsername()) != null || userCANRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("errMsg", "Error: user with such name already exists");
            return "edit_user";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userCANRepo.save(user);

        return "redirect:/login";
    }
}
