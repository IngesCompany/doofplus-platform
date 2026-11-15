package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateAuditFindingCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.Classification;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.FindingStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Audit finding of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class AuditFinding extends AbstractDomainAggregateRoot<AuditFinding> {

    /**
     * The identifier; null until the audit finding is saved.
     */
    private Long id;

    /**
     * The audit code.
     */
    private String auditCode;

    /**
     * The code.
     */
    private String code;

    /**
     * The title.
     */
    private String title;

    /**
     * The classification.
     */
    private Classification classification;

    /**
     * The owner.
     */
    private String owner;

    /**
     * The due at.
     */
    private LocalDate dueAt;

    /**
     * The status.
     */
    private FindingStatus status;

    /**
     * The observation.
     */
    private String observation;

    /**
     * The response.
     */
    private String response;

    /**
     * The closure requirement.
     */
    private String closureRequirement;

    private AuditFinding() {
    }

    /**
     * Creates the audit finding from its command.
     *
     * @param command the data of the audit finding
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public AuditFinding(CreateAuditFindingCommand command) {
        apply(command.auditCode(), command.code(), command.title(), command.classification(), command.owner(), command.dueAt(), command.status(), command.observation(), command.response(), command.closureRequirement());
    }

    /**
     * Rebuilds a saved audit finding without validating it again.
     *
     * @return the audit finding
     */
    public static AuditFinding reconstitute(Long id, String auditCode, String code, String title, Classification classification, String owner, LocalDate dueAt, FindingStatus status, String observation, String response, String closureRequirement) {
        var auditFinding = new AuditFinding();
        auditFinding.id = id;
        auditFinding.auditCode = auditCode;
        auditFinding.code = code;
        auditFinding.title = title;
        auditFinding.classification = classification;
        auditFinding.owner = owner;
        auditFinding.dueAt = dueAt;
        auditFinding.status = status;
        auditFinding.observation = observation;
        auditFinding.response = response;
        auditFinding.closureRequirement = closureRequirement;
        return auditFinding;
    }

    private void apply(String auditCode, String code, String title, Classification classification, String owner, LocalDate dueAt, FindingStatus status, String observation, String response, String closureRequirement) {
        if (auditCode == null || auditCode.isBlank()) throw new IllegalArgumentException("quality.auditFinding.error.auditCode.notBlank");
        this.auditCode = auditCode == null ? "" : auditCode.trim();
        if (code == null || code.isBlank()) throw new IllegalArgumentException("quality.auditFinding.error.code.notBlank");
        this.code = code == null ? "" : code.trim();
        if (title == null || title.isBlank()) throw new IllegalArgumentException("quality.auditFinding.error.title.notBlank");
        this.title = title == null ? "" : title.trim();
        this.classification = classification != null ? classification : Classification.MINOR;
        this.owner = owner == null ? "" : owner.trim();
        this.dueAt = dueAt;
        this.status = status != null ? status : FindingStatus.EVIDENCE_PENDING;
        this.observation = observation == null ? "" : observation.trim();
        this.response = response == null ? "" : response.trim();
        this.closureRequirement = closureRequirement == null ? "" : closureRequirement.trim();
    }
}
