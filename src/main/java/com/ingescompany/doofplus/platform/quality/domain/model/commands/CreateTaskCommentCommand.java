package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import java.time.LocalDateTime;

/**
 * Command to create a task comment. Null values take the defaults of the contract.
 */
public record CreateTaskCommentCommand(
        String recordCode,
        String author,
        String initials,
        LocalDateTime postedAt,
        String text) {
}
