package com.ingescompany.doofplus.platform.quality.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.quality.domain.model.aggregates.RegulatoryReport;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.RegulatoryReportResource;

public class RegulatoryReportResourceFromEntityAssembler {

    public static RegulatoryReportResource toResourceFromEntity(RegulatoryReport entity) {
        if (entity == null) {
            return null;
        }

        return new RegulatoryReportResource(
                entity.getId(),
                entity.getCode(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getRegulatoryAuthority(),
                entity.getFileReference(),
                entity.getDueDate(),
                entity.getStatus() != null ? entity.getStatus().name() : null,
                entity.getPreparedBy(),
                entity.getSubmittedAt()
        );
    }
}