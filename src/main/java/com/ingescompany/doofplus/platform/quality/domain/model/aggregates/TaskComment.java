package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "task_comments")
@NoArgsConstructor
public class TaskComment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID taskId;

    @Column(nullable = false, length = 2000)
    private String content;

    private String author;

    private LocalDateTime createdAt;

    public TaskComment(UUID taskId, String content, String author) {
        this.taskId = taskId;
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
    }
}