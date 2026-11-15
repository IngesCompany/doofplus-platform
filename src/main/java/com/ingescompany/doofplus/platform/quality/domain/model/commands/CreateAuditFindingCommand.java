package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.Classification;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.FindingStatus;
import java.time.LocalDate;

/**
 * Command to create a audit finding. Null values take the defaults of the contract.
 */
public record CreateAuditFindingCommand(
        String auditCode,
        String code,
        String title,
        Classification classification,
        String owner,
        LocalDate dueAt,
        FindingStatus status,
        String observation,
        String response,
        String closureRequirement) {
}
