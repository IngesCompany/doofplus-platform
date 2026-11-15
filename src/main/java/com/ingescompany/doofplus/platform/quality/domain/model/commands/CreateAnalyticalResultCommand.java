package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ApprovalState;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ResultStatus;

/**
 * Command to create a analytical result. Null values take the defaults of the contract.
 */
public record CreateAnalyticalResultCommand(
        String batchCode,
        String sampleCode,
        String reportCode,
        String test,
        String protocol,
        String result,
        String specification,
        String analyst,
        ResultStatus status,
        ApprovalState approval) {
}
