package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.TaskStatus;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Row of the collaboration_tasks table, as in db-06-quality.
 */
@Entity
@Table(name = "collaboration_tasks")
@Getter
@Setter
@NoArgsConstructor
public class CollaborationTaskPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "record_code", length = 60, nullable = false)
    private String recordCode;

    @Column(name = "detail", length = 255, nullable = false)
    private String detail;

    @Column(name = "owner", length = 100, nullable = false)
    private String owner;

    @Column(name = "due_at")
    private LocalDateTime dueAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 24, nullable = false)
    private TaskStatus status;
}
