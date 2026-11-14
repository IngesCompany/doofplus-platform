package com.ingescompany.doofplus.platform.monitoring.domain.model.queries;

/**
 * Query to retrieve equipment by persistent ID.
 *
 * @param id equipment ID
 */
public record GetEquipmentByIdQuery(Long id) {
    public GetEquipmentByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("monitoring.equipment.error.id.invalid");
        }
    }
}
