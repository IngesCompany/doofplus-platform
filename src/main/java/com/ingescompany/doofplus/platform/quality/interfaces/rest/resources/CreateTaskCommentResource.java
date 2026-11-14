package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

public record CreateTaskCommentResource(
        Long taskId,
        Long authorId,
        String content
) {
}