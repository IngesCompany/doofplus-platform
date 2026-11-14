package com.ingescompany.doofplus.platform.monitoring.domain.exceptions;

public class EquipmentNotFoundException extends RuntimeException {
    public EquipmentNotFoundException(Long id) {
        super("Equipment with id " + id + " not found");
    }

    public EquipmentNotFoundException(String code) {
        super("Equipment with code " + code + " not found");
    }
}
