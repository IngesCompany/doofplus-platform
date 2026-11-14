package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.EventTone;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA entity of the {@code batch_events} table (timeline of a batch). The {@code production_batch_id}
 * foreign key is mapped by {@link ProductionBatchPersistenceEntity}.
 */
@Entity
@Table(name = "batch_events")
@Getter
@Setter
@NoArgsConstructor
public class BatchEventPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "type", nullable = false, length = 40)
    private String type;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "tone", length = 10)
    private EventTone tone;

    @Column(name = "performed_by")
    private Long performedBy;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;
}
