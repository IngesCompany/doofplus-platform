package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities.RegulatoryReportPersistenceEntity;

/**
 * Converts the regulatory reports between the domain and the persistence entity.
 */
public final class RegulatoryReportPersistenceAssembler {

    private RegulatoryReportPersistenceAssembler() {
    }

    /**
     * Copies the data of the regulatory report to a row.
     *
     * @param regulatoryReport    the regulatory report
     * @param entity the row, new or already loaded
     * @return the same row
     */
    public static RegulatoryReportPersistenceEntity toEntity(RegulatoryReport regulatoryReport, RegulatoryReportPersistenceEntity entity) {
        entity.setCode(regulatoryReport.getCode());
        entity.setType(regulatoryReport.getType());
        entity.setScope(regulatoryReport.getScope());
        entity.setOwner(regulatoryReport.getOwner());
        entity.setStatus(regulatoryReport.getStatus());
        entity.setTemplate(regulatoryReport.getTemplate());
        entity.setFormat(regulatoryReport.getFormat());
        entity.setCreatedOn(regulatoryReport.getCreatedAt());
        return entity;
    }

    /**
     * Rebuilds the regulatory report of a row.
     *
     * @param entity the row
     * @return the regulatory report
     */
    public static RegulatoryReport toDomain(RegulatoryReportPersistenceEntity entity) {
        return RegulatoryReport.reconstitute(entity.getId(), entity.getCode(), entity.getType(), entity.getScope(), entity.getOwner(), entity.getStatus(), entity.getTemplate(), entity.getFormat(), entity.getCreatedOn());
    }
}
