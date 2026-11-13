package com.ingescompany.doofplus.platform.organizations.domain.model.entities;

import lombok.Getter;

/**
 * Manufacturing plant operated by an organization.
 */
@Getter
public class Plant {
    private Long id;
    private String name;
    private String address;

    public Plant(String name, String address) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("organizations.error.plant.name.notBlank");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("organizations.error.plant.address.notBlank");
        }
        this.name = name;
        this.address = address;
    }

    public Plant(Long id, String name, String address) {
        this(name, address);
        this.id = id;
    }
}
