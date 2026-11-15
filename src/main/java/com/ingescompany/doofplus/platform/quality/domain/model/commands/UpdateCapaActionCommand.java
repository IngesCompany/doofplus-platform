package com.ingescompany.doofplus.platform.quality.domain.model.commands;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.ActionStatus;
import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaType;
import java.time.LocalDate;

/**
 * Command to replace all the data of a capa action.
 *
 * @param capaActionId the identifier of the capa action
 */
public record UpdateCapaActionCommand(
        Long capaActionId,
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
