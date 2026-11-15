package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaStatus;
import java.time.LocalDate;

/**
 * Command to create a capa plan. Null values take the defaults of the contract.
 */
public record CreateCapaPlanCommand(
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
