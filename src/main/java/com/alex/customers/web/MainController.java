package com.alex.customers.web;

import com.alex.customers.model.User;
import com.alex.customers.model.UserCAN;
import com.alex.customers.model.UserUS;
import com.alex.customers.repo.UserCANRepo;
import com.alex.customers.repo.UserUSRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
@SessionAttributes("user")
public class MainController {
    private UserUSRepo userUSRepo;
    private UserCANRepo userCANRepo;
    private PasswordEncoder encoder;

    public MainController(UserUSRepo userUSRepo, UserCANRepo userCANRepo, PasswordEncoder encoder) {
        this.userUSRepo = userUSRepo;
        this.userCANRepo = userCANRepo;
        this.encoder = encoder;
    }

    @ModelAttribute
    public void addDataToModel(Model model) {
        model.addAttribute("us", User.Country.USA);
        model.addAttribute("can", User.Country.CANADA);
    }

    @GetMapping("/us")
    public String createUserUS(Model model) {
        User user = new UserUS();
        user.setCountry(User.Country.USA);
        model.addAttribute("user", user);

        return "register";
    }

    @GetMapping("/can")
    public String createUserCAN(Model model) {
        User user = new UserCAN();
        user.setCountry(User.Country.CANADA);
        model.addAttribute("user", user);

        return "register";
    }

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

}
