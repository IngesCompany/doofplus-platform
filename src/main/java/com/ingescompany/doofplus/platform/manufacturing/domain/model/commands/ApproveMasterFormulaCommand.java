package com.ingescompany.doofplus.platform.manufacturing.domain.model.commands;

/**
 * Command to approve a master formula. The version approved before becomes locked.
 *
 * @param masterFormulaId master formula identifier
 * @param approvedBy      name of the person who approves it
 */
public record ApproveMasterFormulaCommand(Long masterFormulaId, String approvedBy) {
    public ApproveMasterFormulaCommand {
        if (masterFormulaId == null || masterFormulaId <= 0) throw new IllegalArgumentException("manufacturing.error.id.invalid");
        if (approvedBy == null || approvedBy.isBlank()) throw new IllegalArgumentException("manufacturing.error.formula.approvedBy.notBlank");
        approvedBy = approvedBy.trim();
    }
}
