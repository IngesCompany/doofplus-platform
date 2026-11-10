package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskComment extends AbstractDomainAggregateRoot<TaskComment> {

    private Long id;
    private Long taskId;
    private Long authorId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TaskComment(Long taskId, Long authorId, String content) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task identifier is required");
        }
        if (authorId == null) {
            throw new IllegalArgumentException("Comment author is required");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Comment content is required");
        }

        this.taskId = taskId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public static TaskComment reconstitute(
            Long id, Long taskId, Long authorId, String content,
            LocalDateTime createdAt, LocalDateTime updatedAt) {

        TaskComment comment = new TaskComment(taskId, authorId, content);
        comment.id = id;
        comment.createdAt = createdAt;
        comment.updatedAt = updatedAt;
        return comment;
    }

    public void updateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Comment content is required");
        }
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}