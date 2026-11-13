package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.RegulatoryReportPersistenceEntity;

public class RegulatoryReportPersistenceAssembler {

    public static RegulatoryReport toDomain(RegulatoryReportPersistenceEntity entity) {
        return RegulatoryReport.reconstitute(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getRegulatoryAuthority(),
                entity.getFileReference(),
                entity.getDueDate(),
                entity.getStatus(),
                entity.getPreparedBy(),
                entity.getSubmittedAt()
        );
    }

    public static RegulatoryReportPersistenceEntity toEntity(RegulatoryReport report) {
        RegulatoryReportPersistenceEntity entity = new RegulatoryReportPersistenceEntity();

        entity.setCode(report.getCode());
        entity.setTitle(report.getTitle());
        entity.setDescription(report.getDescription());
        entity.setRegulatoryAuthority(report.getRegulatoryAuthority());
        entity.setFileReference(report.getFileReference());
        entity.setDueDate(report.getDueDate());
        entity.setStatus(report.getStatus());
        entity.setPreparedBy(report.getPreparedBy());
        entity.setSubmittedAt(report.getSubmittedAt());

        return entity;
    }
}