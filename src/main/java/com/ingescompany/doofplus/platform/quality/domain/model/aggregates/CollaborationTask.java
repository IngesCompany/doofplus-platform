package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CollaborationTaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "collaboration_tasks")
@NoArgsConstructor
public class CollaborationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String assignedTo;

    private UUID relatedDeviationId;

    private UUID relatedAuditId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CollaborationTaskStatus status;

    private LocalDateTime dueDate;

    private LocalDateTime createdAt;

    public CollaborationTask(String title, String description,
                             String assignedTo, UUID relatedDeviationId,
                             UUID relatedAuditId, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.relatedDeviationId = relatedDeviationId;
        this.relatedAuditId = relatedAuditId;
        this.dueDate = dueDate;
        this.status = CollaborationTaskStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void updateStatus(CollaborationTaskStatus status) {
        this.status = status;
    }

    public void assignTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}