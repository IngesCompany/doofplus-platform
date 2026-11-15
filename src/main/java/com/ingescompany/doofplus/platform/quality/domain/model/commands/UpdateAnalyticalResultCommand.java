package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ApprovalState;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ResultStatus;

/**
 * Command to replace all the data of a analytical result.
 *
 * @param analyticalResultId the identifier of the analytical result
 */
public record UpdateAnalyticalResultCommand(
        Long analyticalResultId,
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
