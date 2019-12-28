package com.alex.customers.web;

import com.alex.customers.model.User;
import com.alex.customers.model.UserCAN;
import com.alex.customers.model.UserUS;
import com.alex.customers.repo.StateUSRepo;
import com.alex.customers.repo.UserCANRepo;
import com.alex.customers.repo.UserUSRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
//@SessionAttributes("user")
public class MainController {
    private UserUSRepo userUSRepo;
    private UserCANRepo userCANRepo;
    private StateUSRepo stateUSRepo;
    private PasswordEncoder encoder;

    public MainController(UserUSRepo userUSRepo, UserCANRepo userCANRepo, StateUSRepo stateUSRepo, PasswordEncoder encoder) {
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
    public String countryChoice() {
        return "country";
    }
/*
    @GetMapping("/register/{country}")
    public String createUser(@PathVariable("country") String country, Model model) {
        if (country.equals("us")) model.addAttribute("user", new UserUS());
        else model.addAttribute("user", new UserCAN());

        return "register";
    }
*/

    @GetMapping("/register/us")
    public String createUserUS(Model model) {
        model.addAttribute("user", new UserUS());
        return "register";
    }

    @GetMapping("/register/can")
    public String createUserCAN(Model model) {
        model.addAttribute("user", new UserCAN());
        return "register";
    }
/*
    @PostMapping("/us")
    public String registerUserUS(@ModelAttribute UserUS user, Model model) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("errMsg", "Error: check your password and try again");
            return "register";
        }

        if (userUSRepo.findByUsername(user.getUsername()) != null || userCANRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("errMsg", "Error: user with such name already exists");
            return "register";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userUSRepo.save(user);
        //System.out.println(userUSRepo.findByUsername(user.getUsername()));

        return "redirect:/login";
    }

    @PostMapping("/can")
    public String registerUserCAN(@ModelAttribute UserCAN user, Model model) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("errMsg", "Error: check your password and try again");
            return "register";
        }

        if (userUSRepo.findByUsername(user.getUsername()) != null || userCANRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("errMsg", "Error: user with such name already exists");
            return "register";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userCANRepo.save(user);
        //System.out.println(userCANRepo.findByUsername(user.getUsername()));

        return "redirect:/login";
    }
*/
}
