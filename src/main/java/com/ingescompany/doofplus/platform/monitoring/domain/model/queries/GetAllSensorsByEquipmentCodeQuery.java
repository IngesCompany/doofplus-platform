package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

/**
 * Query to retrieve all sensors installed on a piece of equipment.
 *
 * @param equipmentCode equipment code
 */
public record GetAllSensorsByEquipmentCodeQuery(String equipmentCode) {
    public GetAllSensorsByEquipmentCodeQuery {
        if (equipmentCode == null || equipmentCode.isBlank()) {
            throw new IllegalArgumentException("monitoring.equipment.error.code.notBlank");
        }
    }
}
