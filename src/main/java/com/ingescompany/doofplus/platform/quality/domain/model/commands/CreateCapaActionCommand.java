package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ActionStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaType;
import java.time.LocalDate;

/**
 * Command to create a capa action. Null values take the defaults of the contract.
 */
public record CreateCapaActionCommand(
        String capaCode,
        String code,
        CapaType type,
        String title,
        String owner,
        LocalDate dueAt,
        ActionStatus status,
        String evidence,
        Boolean evidenceAttached) {
}
