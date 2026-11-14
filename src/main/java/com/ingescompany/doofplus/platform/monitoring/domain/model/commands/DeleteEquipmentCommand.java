package com.ingescompany.doofplus.platform.monitoring.domain.model.commands;

/**
 * Command to remove a piece of equipment.
 *
 * @param id equipment persistent ID
 */
public record DeleteEquipmentCommand(Long id) {
    public DeleteEquipmentCommand {
        if (id == null || id <= 0) throw new IllegalArgumentException("Equipment id must be a positive number");
    }
}
