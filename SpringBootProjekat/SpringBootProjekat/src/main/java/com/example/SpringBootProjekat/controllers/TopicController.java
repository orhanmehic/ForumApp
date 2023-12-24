package com.example.SpringBootProjekat.controllers;

import com.example.SpringBootProjekat.entities.Comment;
import com.example.SpringBootProjekat.entities.Topic;
import com.example.SpringBootProjekat.entities.UserEntity;
import com.example.SpringBootProjekat.services.CommentService;
import com.example.SpringBootProjekat.services.TopicService;
import com.example.SpringBootProjekat.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/topic")
public class TopicController {
    private final TopicService topicService;
    private final UserService userService;

    private final CommentService commentService;

    public TopicController(TopicService topicService, UserService userService, CommentService commentService) {

        this.topicService = topicService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public String getCommentsPage(@PathVariable Long id, Model model) {
        Topic topic = topicService.getTopicById(id);

        if (topic == null) {
            return "redirect:/home";
        }

        model.addAttribute("topic", topic);
        return "comments";
    }
    @PostMapping("/{id}/addComment")
    public String addComment(@PathVariable Long id, @RequestParam("content") String content, Model model, HttpSession session){

        Integer userid = (Integer) session.getAttribute("userid");
        commentService.addCommentToTopic(userid, id, content);
        return "redirect:/topic/" + id;
    }

}
