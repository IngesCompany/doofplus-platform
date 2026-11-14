package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.DosageForm;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity of the {@code products} table.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "code", nullable = false, unique = true, length = 30)
    private String code;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "dosage_form", nullable = false, length = 20)
    private DosageForm dosageForm;
}
