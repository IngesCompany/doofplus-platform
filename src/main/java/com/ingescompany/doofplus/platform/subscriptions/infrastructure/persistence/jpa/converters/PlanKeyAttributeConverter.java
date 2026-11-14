package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.converters;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PlanKey;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PlanKeyAttributeConverter implements AttributeConverter<PlanKey, String> {
    @Override
    public String convertToDatabaseColumn(PlanKey attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public PlanKey convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new PlanKey(dbData);
    }
}
