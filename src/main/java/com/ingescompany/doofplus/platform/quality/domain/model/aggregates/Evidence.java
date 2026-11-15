package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.commands.UpdateEvidenceCommand;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.EvidenceStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Evidence of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class Evidence extends AbstractDomainAggregateRoot<Evidence> {

    /**
     * The identifier; null until the evidence is saved.
     */
    private Long id;

    /**
     * The record code.
     */
    private String recordCode;

    /**
     * The file name.
     */
    private String fileName;

    /**
     * The detail.
     */
    private String detail;

    /**
     * The status.
     */
    private EvidenceStatus status;

    private Evidence() {
    }

    /**
     * Creates the evidence from its command.
     *
     * @param command the data of the evidence
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public Evidence(CreateEvidenceCommand command) {
        apply(command.recordCode(), command.fileName(), command.detail(), command.status());
    }

    /**
     * Rebuilds a saved evidence without validating it again.
     *
     * @return the evidence
     */
    public static Evidence reconstitute(Long id, String recordCode, String fileName, String detail, EvidenceStatus status) {
        var evidence = new Evidence();
        evidence.id = id;
        evidence.recordCode = recordCode;
        evidence.fileName = fileName;
        evidence.detail = detail;
        evidence.status = status;
        return evidence;
    }

    /**
     * Replaces the data of the evidence with the data of the command.
     *
     * @param command the new data
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public void update(UpdateEvidenceCommand command) {
        apply(command.recordCode(), command.fileName(), command.detail(), command.status());
    }

    /**
     * Reviewed evidence cannot be removed, so the investigation keeps its integrity.
     *
     * @return true when the evidence is not reviewed
     */
    public boolean canBeRemoved() {
        return status != EvidenceStatus.REVIEWED;
    }

    private void apply(String recordCode, String fileName, String detail, EvidenceStatus status) {
        if (recordCode == null || recordCode.isBlank()) throw new IllegalArgumentException("quality.evidence.error.recordCode.notBlank");
        this.recordCode = recordCode == null ? "" : recordCode.trim();
        if (fileName == null || fileName.isBlank()) throw new IllegalArgumentException("quality.evidence.error.fileName.notBlank");
        this.fileName = fileName == null ? "" : fileName.trim();
        this.detail = detail == null ? "" : detail.trim();
        this.status = status != null ? status : EvidenceStatus.ATTACHED;
    }
}
