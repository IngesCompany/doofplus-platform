package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.SensorCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SensorCodeAttributeConverter implements AttributeConverter<SensorCode, String> {

    @Override
    public String convertToDatabaseColumn(SensorCode attribute) {
        return attribute != null ? attribute.value() : null;
    }

    @Override
    public SensorCode convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.isBlank() ? new SensorCode(dbData) : null;
    }
}
