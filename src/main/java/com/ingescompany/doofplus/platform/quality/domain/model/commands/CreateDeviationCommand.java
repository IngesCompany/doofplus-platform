package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.Classification;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationStatus;
import java.time.LocalDateTime;

/**
 * Command to create a deviation. Null values take the defaults of the contract.
 */
public record CreateDeviationCommand(
        String code,
        String title,
        String batchCode,
        String orderCode,
        String summary,
        Classification classification,
        String containment,
        Integer severity,
        Integer likelihood,
        Integer detectability,
        DeviationStatus status,
        String rootCause,
        String capaCode,
        String reportedBy,
        LocalDateTime reportedAt,
        String owner,
        LocalDateTime dueAt) {
}
