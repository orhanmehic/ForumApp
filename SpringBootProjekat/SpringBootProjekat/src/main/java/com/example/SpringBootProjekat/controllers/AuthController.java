package com.example.SpringBootProjekat.controllers;

import com.example.SpringBootProjekat.entities.UserEntity;
import com.example.SpringBootProjekat.repositories.UserRepository;
import com.example.SpringBootProjekat.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository= userRepository;
    }

    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserEntity user) {
        System.out.println("registrovan");
        if (user.getPassword().isEmpty() || !userService.registerUser(user)) {
            return "register";
        } else {
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserEntity user, Model model, HttpSession session) {
        if (userService.authenticateUser(user.getUsername(), user.getPassword())) {
            UserEntity authenticatedUser = userRepository.findByUsername(user.getUsername()).orElseThrow();
            session.setAttribute("username", authenticatedUser.getUsername());
            session.setAttribute("userid", authenticatedUser.getId());
            session.setAttribute("role", authenticatedUser.getRole());
            if (authenticatedUser.getRole().equals("admin")) {
                return "redirect:/admin/";
            }
            return "redirect:/home/";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logoutUser(HttpSession session){
        session.invalidate();
        return "redirect:/home/";

    }


}
