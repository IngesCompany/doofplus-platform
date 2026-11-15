package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.GapStatus;

/**
 * Command to create a traceability gap. Null values take the defaults of the contract.
 */
public record CreateTraceabilityGapCommand(
        String batchCode,
        String product,
        String missingRecord,
        String detail,
        GapStatus status) {
}
