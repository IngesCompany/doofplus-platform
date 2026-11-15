package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.TaskStatus;
import java.time.LocalDateTime;

/**
 * Command to create a collaboration task. Null values take the defaults of the contract.
 */
public record CreateCollaborationTaskCommand(
        String title,
        String recordCode,
        String detail,
        String owner,
        LocalDateTime dueAt,
        TaskStatus status) {
}
