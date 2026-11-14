package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for Reading.
 *
 * <p>Extends {@link AuditableAbstractPersistenceEntity} for ID and auditing timestamps.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "readings")
public class ReadingPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String sensorCode;

    @Column
    private String batchCode;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private String recordedAt;

    @Column(nullable = false)
    private String source;
}
