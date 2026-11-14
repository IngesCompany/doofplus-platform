package com.ingescompany.doofplus.platform.quality.interfaces.rest.resources;

import java.time.LocalDateTime;

public record TaskCommentResource(
        Long id,
        Long taskId,
        Long authorId,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}