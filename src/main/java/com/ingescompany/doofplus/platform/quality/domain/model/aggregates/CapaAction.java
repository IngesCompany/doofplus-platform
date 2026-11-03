package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name = "quality_capa_actions")
@NoArgsConstructor
public class CapaAction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID capaPlanId;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String assignedTo;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CapaStatus status;

    public CapaAction(
            UUID capaPlanId,
            String title,
            String description,
            String assignedTo,
            LocalDate dueDate
    ) {
        this.capaPlanId = capaPlanId;
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.dueDate = dueDate;
        this.status = CapaStatus.OPEN;
    }

    public void updateStatus(CapaStatus status) {
        this.status = status;
    }

    public void assignTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}