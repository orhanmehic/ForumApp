package com.example.SpringBootProjekat.controllers;



import com.example.SpringBootProjekat.entities.Topic;
import com.example.SpringBootProjekat.services.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final TopicService topicService;

    public HomeController(TopicService topicService) {
        this.topicService = topicService;
    }
    @GetMapping("/")
    public String getTopics(Model model, HttpSession session){
        if(session.getAttribute("username")!=null){
            model.addAttribute("username", session.getAttribute("username"));
        }
        List<Topic> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        return "home";
    }

}
