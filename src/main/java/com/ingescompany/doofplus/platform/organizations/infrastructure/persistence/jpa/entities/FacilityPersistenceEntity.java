package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.FacilityStatus;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.FacilityType;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "facilities")
@Getter
@Setter
@NoArgsConstructor
public class FacilityPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private OrganizationPersistenceEntity organization;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private FacilityType type;

    @Column(name = "timezone", nullable = false, length = 40)
    private String timezone;

    @Column(name = "owner_name", length = 150)
    private String ownerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private FacilityStatus status;
}
