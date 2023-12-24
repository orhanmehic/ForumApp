package com.example.SpringBootProjekat.controllers;

import com.example.SpringBootProjekat.entities.Topic;
import com.example.SpringBootProjekat.entities.UserEntity;
import com.example.SpringBootProjekat.services.TopicService;
import com.example.SpringBootProjekat.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final TopicService topicService;

    public AdminController(UserService userService, TopicService topicService) {
        this.userService = userService;
        this.topicService = topicService;
    }


    @GetMapping("/")
    public String adminPanel(HttpSession session, Model model) {
        Object usernameAttribute = session.getAttribute("username");
        Object roleAttribute = session.getAttribute("role");
        List<UserEntity> users = userService.getAllUsers();
        List<Topic> topics = topicService.getAllTopics();

        // Check if the user is logged in and has the "admin" role
        if (usernameAttribute == null ||  !("admin".equals(roleAttribute.toString()))) {
            return "redirect:/home/";
        } else {

            model.addAttribute("users", users);
            model.addAttribute("topics", topics);
           return "adminpanel";
        }
    }

    @RequestMapping(value = "/deleteUser/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/"; // Redirect back to the admin panel after deletion
    }

    @PostMapping("/deleteTopic/{topicId}")
    public String deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopic(topicId);
        return "redirect:/admin/"; // Redirect back to the admin panel after deletion
    }

    @PostMapping("/addTopic")
    public String addTopic(@RequestParam String title, @RequestParam String description) {
        topicService.addTopic(title, description);
        return "redirect:/admin/"; // Redirect back to the admin panel after adding a new topic
    }
}
