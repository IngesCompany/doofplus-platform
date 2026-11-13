package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.OrganizationStatus;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.converters.RucPersistenceConverter;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
public class OrganizationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "legal_name", nullable = false)
    private String legalName;

    @Convert(converter = RucPersistenceConverter.class)
    @Column(name = "ruc", nullable = false, unique = true, length = 11)
    private Ruc ruc;

    @Column(name = "region", nullable = false, length = 60)
    private String region;

    @Column(name = "owner_name", nullable = false, length = 150)
    private String ownerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 25)
    private OrganizationStatus status;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id")
    private List<FacilityPersistenceEntity> facilities = new ArrayList<>();
}
