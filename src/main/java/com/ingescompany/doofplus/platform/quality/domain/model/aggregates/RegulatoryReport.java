package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.valueobjects.RegulatoryReportStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "regulatory_reports")
@NoArgsConstructor
public class RegulatoryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String title;

    private String authority;

    private String description;

    private String fileReference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegulatoryReportStatus status;

    private String createdBy;

    private LocalDateTime createdAt;

    public RegulatoryReport(String code, String title, String authority,
                            String description, String createdBy) {
        this.code = code;
        this.title = title;
        this.authority = authority;
        this.description = description;
        this.createdBy = createdBy;
        this.status = RegulatoryReportStatus.DRAFT;
        this.createdAt = LocalDateTime.now();
    }

    public void updateStatus(RegulatoryReportStatus status) {
        this.status = status;
    }

    public void attachFile(String fileReference) {
        this.fileReference = fileReference;
    }
}