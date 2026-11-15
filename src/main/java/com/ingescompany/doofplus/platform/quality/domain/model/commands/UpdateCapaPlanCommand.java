package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaStatus;
import java.time.LocalDate;

/**
 * Command to replace all the data of a capa plan.
 *
 * @param capaPlanId the identifier of the capa plan
 */
public record UpdateCapaPlanCommand(
        Long capaPlanId,
        String code,
        String title,
        String sourceDeviation,
        String owner,
        String reviewer,
        String rootCause,
        String acceptanceCriterion,
        LocalDate effectivenessCheckAt,
        CapaStatus status) {
}
