package com.ingescompany.doofplus.platform.quality.domain.model.commands;

public record CreateTaskCommentCommand(
        Long taskId,
        Long authorId,
        String content
) {
}