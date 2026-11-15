package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AuditStatus;

/**
 * Command to create a audit. Null values take the defaults of the contract.
 */
public record CreateAuditCommand(
        String code,
        String title,
        String period,
        Integer areasReviewed,
        Integer areasTotal,
        AuditStatus status) {
}
