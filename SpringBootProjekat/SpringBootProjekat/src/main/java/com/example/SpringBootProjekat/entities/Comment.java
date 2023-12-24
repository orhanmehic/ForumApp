package com.example.SpringBootProjekat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "topicId", nullable = false)
    private Topic topic;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    public Comment(UserEntity user, Topic topic, String content) {
        this.user = user;
        this.topic = topic;
        this.content = content;
    }
}
