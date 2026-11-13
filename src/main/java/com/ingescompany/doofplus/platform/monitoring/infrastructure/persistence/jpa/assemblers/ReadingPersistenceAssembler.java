package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Reading;
import com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.entities.ReadingPersistenceEntity;

/**
 * Assembler to translate between {@link Reading} domain aggregate and {@link ReadingPersistenceEntity}.
 */
public final class ReadingPersistenceAssembler {

    private ReadingPersistenceAssembler() {
    }

    public static Reading toDomainFromPersistence(ReadingPersistenceEntity entity) {
        if (entity == null) return null;
        return Reading.reconstitute(
                entity.getId(),
                entity.getSensorCode(),
                entity.getBatchCode(),
                entity.getValue(),
                entity.getRecordedAt(),
                entity.getSource()
        );
    }

    public static ReadingPersistenceEntity toPersistenceFromDomain(Reading domain) {
        if (domain == null) return null;
        var entity = new ReadingPersistenceEntity();
        entity.setId(domain.getId());
        entity.setSensorCode(domain.getSensorCode());
        entity.setBatchCode(domain.getBatchCode());
        entity.setValue(domain.getValue());
        entity.setRecordedAt(domain.getRecordedAt());
        entity.setSource(domain.getSource());
        return entity;
    }
}
