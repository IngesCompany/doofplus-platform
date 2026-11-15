package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateAnalyticalResultCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ApprovalState;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ResultStatus;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.UpdateAnalyticalResultResource;

/**
 * Converts the body to replace a analytical result to its command.
 */
public final class UpdateAnalyticalResultCommandFromResourceAssembler {

    private UpdateAnalyticalResultCommandFromResourceAssembler() {
    }

    /**
     * Converts the body to its command.
     *
     * @param analyticalResultId the identifier of the analytical result
     * @param resource the body
     * @return the command
     * @throws IllegalArgumentException with a message key when an enum or a date is not valid
     */
    public static UpdateAnalyticalResultCommand toCommandFromResource(Long analyticalResultId, UpdateAnalyticalResultResource resource) {
        return new UpdateAnalyticalResultCommand(
                analyticalResultId,
                resource.batchCode(),
                resource.sampleCode(),
                resource.reportCode(),
                resource.test(),
                resource.protocol(),
                resource.result(),
                resource.specification(),
                resource.analyst(),
                QualityValueParser.toEnum(ResultStatus.class, resource.status(), "quality.analyticalResult.error.status.invalid"),
                QualityValueParser.toEnum(ApprovalState.class, resource.approval(), "quality.analyticalResult.error.approval.invalid"));
    }
}
