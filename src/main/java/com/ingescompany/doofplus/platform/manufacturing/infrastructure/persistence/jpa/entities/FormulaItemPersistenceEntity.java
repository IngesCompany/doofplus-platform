package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.FormulaItemKind;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity of the {@code formula_items} table. The {@code master_formula_id} foreign key is mapped by {@link MasterFormulaPersistenceEntity}.
 */
@Entity
@Table(name = "formula_items")
@Getter
@Setter
@NoArgsConstructor
public class FormulaItemPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = false, length = 10)
    private FormulaItemKind kind;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "role", length = 40)
    private String role;

    @Column(name = "target", length = 40)
    private String target;

    @Column(name = "tolerance", length = 40)
    private String tolerance;

    @Column(name = "control", length = 150)
    private String control;
}
