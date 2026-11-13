package com.ingescompany.doofplus.platform.organizations.domain.model.entities;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.FacilityStatus;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.FacilityType;
import lombok.Getter;

/**
 * Facility operated by an organization: a manufacturing plant or a quality control laboratory.
 */
@Getter
public class Facility {
    private Long id;
    private String name;
    private FacilityType type;
    private String timezone;
    private String ownerName;
    private FacilityStatus status;

    public Facility(String name, FacilityType type, String timezone, String ownerName) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("organizations.error.facility.name.notBlank");
        }
        if (type == null) {
            throw new IllegalArgumentException("organizations.error.facility.type.invalid");
        }
        if (timezone == null || timezone.isBlank()) {
            throw new IllegalArgumentException("organizations.error.facility.timezone.notBlank");
        }
        this.name = name;
        this.type = type;
        this.timezone = timezone;
        this.ownerName = ownerName;
        this.status = FacilityStatus.ACTIVE;
    }

    public Facility(Long id, String name, FacilityType type, String timezone, String ownerName,
                    FacilityStatus status) {
        this(name, type, timezone, ownerName);
        this.id = id;
        this.status = status;
    }
}
