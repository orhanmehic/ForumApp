package com.example.SpringBootProjekat.services;

import com.example.SpringBootProjekat.entities.Comment;
import com.example.SpringBootProjekat.entities.Topic;
import com.example.SpringBootProjekat.entities.UserEntity;
import com.example.SpringBootProjekat.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private final UserService userService;

    private final TopicService topicService;


    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, TopicService topicService) {
        this.topicService = topicService;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    public void addCommentToTopic(Integer userid, Long topicid, String commentContent) {
        Long longUserId= (long) userid;
        UserEntity user = userService.findUserbyId(longUserId);
        Topic topic = topicService.getTopicById(topicid);
        Comment comment = new Comment(user, topic, commentContent);
        commentRepository.save(comment);
    }

}
