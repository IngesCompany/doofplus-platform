package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.CapaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "quality_capa_plans")
@NoArgsConstructor
public class CapaPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID deviationId;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CapaStatus status;

    private LocalDate dueDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public CapaPlan(
            UUID deviationId,
            String title,
            String description,
            LocalDate dueDate
    ) {
        this.deviationId = deviationId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = CapaStatus.OPEN;
        this.createdAt = LocalDateTime.now();
    }

    public void updateStatus(CapaStatus status) {
        this.status = status;
    }
}