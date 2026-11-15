package com.ingescompany.doofplus.platform.quality.domain.model.commands;

/**
 * Command to remove a evidence.
 *
 * @param evidenceId the identifier of the evidence
 */
public record DeleteEvidenceCommand(Long evidenceId) {
}
