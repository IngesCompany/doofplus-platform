package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.Classification;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.DeviationStatus;
import java.time.LocalDateTime;

/**
 * Command to replace all the data of a deviation.
 *
 * @param deviationId the identifier of the deviation
 */
public record UpdateDeviationCommand(
        Long deviationId,
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
