package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.OperationStatus;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity of the {@code operations} table. The {@code production_order_id} foreign key is mapped by {@link ProductionOrderPersistenceEntity}.
 */
@Entity
@Table(name = "operations")
@Getter
@Setter
@NoArgsConstructor
public class OperationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "sequence", nullable = false)
    private int sequence;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "equipment", length = 150)
    private String equipment;

    @Column(name = "progress", nullable = false)
    private int progress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OperationStatus status;

    @Column(name = "reference", length = 100)
    private String reference;
}
