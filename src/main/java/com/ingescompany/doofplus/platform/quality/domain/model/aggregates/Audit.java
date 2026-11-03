package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.AuditStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "quality_audits")
@NoArgsConstructor
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String title;

    private String description;

    private String auditor;

    private LocalDate scheduledDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditStatus status;

    private LocalDateTime createdAt;

    public Audit(String code, String title, String description,
                 String auditor, LocalDate scheduledDate) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.auditor = auditor;
        this.scheduledDate = scheduledDate;
        this.status = AuditStatus.PLANNED;
        this.createdAt = LocalDateTime.now();
    }

    public void updateStatus(AuditStatus status) {
        this.status = status;
    }

    public void assignAuditor(String auditor) {
        this.auditor = auditor;
    }
}