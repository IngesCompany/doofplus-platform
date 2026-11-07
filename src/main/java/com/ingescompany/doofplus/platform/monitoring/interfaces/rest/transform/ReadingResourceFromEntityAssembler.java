package com.ingescompany.doofplus.platform.monitoring.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Reading;
import com.ingescompany.doofplus.platform.monitoring.interfaces.rest.resources.ReadingResource;

/**
 * Assembler to translate a {@link Reading} domain aggregate into a {@link ReadingResource}.
 */
public final class ReadingResourceFromEntityAssembler {

    private ReadingResourceFromEntityAssembler() {
    }

    public static ReadingResource toResourceFromEntity(Reading entity) {
        if (entity == null) return null;
        return new ReadingResource(
                entity.getId(),
                entity.getSensorCode(),
                entity.getBatchCode(),
                entity.getValue(),
                entity.getRecordedAt(),
                entity.getSource()
        );
    }
}
