package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTraceabilityGapCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.GapStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Traceability gap of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class TraceabilityGap extends AbstractDomainAggregateRoot<TraceabilityGap> {

    /**
     * The identifier; null until the traceability gap is saved.
     */
    private Long id;

    /**
     * The batch code.
     */
    private String batchCode;

    /**
     * The product.
     */
    private String product;

    /**
     * The missing record.
     */
    private String missingRecord;

    /**
     * The detail.
     */
    private String detail;

    /**
     * The status.
     */
    private GapStatus status;

    private TraceabilityGap() {
    }

    /**
     * Creates the traceability gap from its command.
     *
     * @param command the data of the traceability gap
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public TraceabilityGap(CreateTraceabilityGapCommand command) {
        apply(command.batchCode(), command.product(), command.missingRecord(), command.detail(), command.status());
    }

    /**
     * Rebuilds a saved traceability gap without validating it again.
     *
     * @return the traceability gap
     */
    public static TraceabilityGap reconstitute(Long id, String batchCode, String product, String missingRecord, String detail, GapStatus status) {
        var traceabilityGap = new TraceabilityGap();
        traceabilityGap.id = id;
        traceabilityGap.batchCode = batchCode;
        traceabilityGap.product = product;
        traceabilityGap.missingRecord = missingRecord;
        traceabilityGap.detail = detail;
        traceabilityGap.status = status;
        return traceabilityGap;
    }

    private void apply(String batchCode, String product, String missingRecord, String detail, GapStatus status) {
        if (batchCode == null || batchCode.isBlank()) throw new IllegalArgumentException("quality.traceabilityGap.error.batchCode.notBlank");
        this.batchCode = batchCode == null ? "" : batchCode.trim();
        this.product = product == null ? "" : product.trim();
        if (missingRecord == null || missingRecord.isBlank()) throw new IllegalArgumentException("quality.traceabilityGap.error.missingRecord.notBlank");
        this.missingRecord = missingRecord == null ? "" : missingRecord.trim();
        this.detail = detail == null ? "" : detail.trim();
        this.status = status != null ? status : GapStatus.OPEN;
    }
}
