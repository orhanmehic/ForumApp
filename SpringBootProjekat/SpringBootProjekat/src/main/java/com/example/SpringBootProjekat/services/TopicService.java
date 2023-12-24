package com.example.SpringBootProjekat.services;

import com.example.SpringBootProjekat.entities.Topic;
import com.example.SpringBootProjekat.repositories.TopicRepository;
import com.example.SpringBootProjekat.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public List<Topic> getAllTopics(){
        return topicRepository.findAll();
    }


    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + id));
    }

    public void deleteTopic(Long id){
        Topic deletionTopic = topicRepository.findById(id).orElseThrow();
        topicRepository.delete(deletionTopic);

    }

    public void addTopic(String title, String desc){
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setDescription(desc);
        topicRepository.save(topic);
    }
}
