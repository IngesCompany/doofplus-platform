package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources;

/**
 * Resource representation of an equipment item.
 */
public record EquipmentResource(
        Long id,
        String code,
        String name,
        String model,
        String location,
        String calibrationStatus,
        String calibrationDue,
        String certificate,
        String nextMaintenance,
        String maintenanceOwner,
        String workOrder,
        String healthNote
) {
}
