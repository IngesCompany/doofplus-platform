package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CollaborationTaskStatus;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "collaboration_tasks")
@Getter
@Setter
@NoArgsConstructor
public class CollaborationTaskPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Long assignedTo;

    private Long createdBy;

    private Long relatedDeviationId;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CollaborationTaskStatus status;

    private LocalDateTime completedAt;
}