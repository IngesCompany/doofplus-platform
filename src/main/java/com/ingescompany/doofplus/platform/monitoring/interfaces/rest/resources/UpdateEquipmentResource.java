package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources;

/**
 * Resource to update an equipment item.
 */
public record UpdateEquipmentResource(
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
