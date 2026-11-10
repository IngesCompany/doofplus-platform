package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;

/**
 * Query to retrieve equipment by equipment code.
 *
 * @param code equipment code
 */
public record GetEquipmentByCodeQuery(EquipmentCode code) {
    public GetEquipmentByCodeQuery {
        if (code == null) {
            throw new IllegalArgumentException("monitoring.equipment.error.code.notBlank");
        }
    }
}
