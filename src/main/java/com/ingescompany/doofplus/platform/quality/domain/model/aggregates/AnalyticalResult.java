package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAnalyticalResultCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateAnalyticalResultCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ApprovalState;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ResultStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Analytical result of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class AnalyticalResult extends AbstractDomainAggregateRoot<AnalyticalResult> {

    /**
     * The identifier; null until the analytical result is saved.
     */
    private Long id;

    /**
     * The batch code.
     */
    private String batchCode;

    /**
     * The sample code.
     */
    private String sampleCode;

    /**
     * The report code.
     */
    private String reportCode;

    /**
     * The test.
     */
    private String test;

    /**
     * The protocol.
     */
    private String protocol;

    /**
     * The result.
     */
    private String result;

    /**
     * The specification.
     */
    private String specification;

    /**
     * The analyst.
     */
    private String analyst;

    /**
     * The status.
     */
    private ResultStatus status;

    /**
     * The approval.
     */
    private ApprovalState approval;

    private AnalyticalResult() {
    }

    /**
     * Creates the analytical result from its command.
     *
     * @param command the data of the analytical result
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public AnalyticalResult(CreateAnalyticalResultCommand command) {
        apply(command.batchCode(), command.sampleCode(), command.reportCode(), command.test(), command.protocol(), command.result(), command.specification(), command.analyst(), command.status(), command.approval());
    }

    /**
     * Rebuilds a saved analytical result without validating it again.
     *
     * @return the analytical result
     */
    public static AnalyticalResult reconstitute(Long id, String batchCode, String sampleCode, String reportCode, String test, String protocol, String result, String specification, String analyst, ResultStatus status, ApprovalState approval) {
        var analyticalResult = new AnalyticalResult();
        analyticalResult.id = id;
        analyticalResult.batchCode = batchCode;
        analyticalResult.sampleCode = sampleCode;
        analyticalResult.reportCode = reportCode;
        analyticalResult.test = test;
        analyticalResult.protocol = protocol;
        analyticalResult.result = result;
        analyticalResult.specification = specification;
        analyticalResult.analyst = analyst;
        analyticalResult.status = status;
        analyticalResult.approval = approval;
        return analyticalResult;
    }

    /**
     * Replaces the data of the analytical result with the data of the command.
     *
     * @param command the new data
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public void update(UpdateAnalyticalResultCommand command) {
        apply(command.batchCode(), command.sampleCode(), command.reportCode(), command.test(), command.protocol(), command.result(), command.specification(), command.analyst(), command.status(), command.approval());
    }

    private void apply(String batchCode, String sampleCode, String reportCode, String test, String protocol, String result, String specification, String analyst, ResultStatus status, ApprovalState approval) {
        if (batchCode == null || batchCode.isBlank()) throw new IllegalArgumentException("quality.analyticalResult.error.batchCode.notBlank");
        this.batchCode = batchCode == null ? "" : batchCode.trim();
        this.sampleCode = sampleCode == null ? "" : sampleCode.trim();
        this.reportCode = reportCode == null ? "" : reportCode.trim();
        if (test == null || test.isBlank()) throw new IllegalArgumentException("quality.analyticalResult.error.test.notBlank");
        this.test = test == null ? "" : test.trim();
        this.protocol = protocol == null ? "" : protocol.trim();
        this.result = result == null ? "" : result.trim();
        this.specification = specification == null ? "" : specification.trim();
        this.analyst = analyst == null ? "" : analyst.trim();
        this.status = status != null ? status : ResultStatus.PENDING;
        this.approval = approval != null ? approval : ApprovalState.REQUESTED;
    }
}
