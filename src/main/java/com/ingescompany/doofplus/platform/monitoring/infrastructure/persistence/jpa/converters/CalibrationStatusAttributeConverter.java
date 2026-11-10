package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.CalibrationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CalibrationStatusAttributeConverter implements AttributeConverter<CalibrationStatus, String> {

    @Override
    public String convertToDatabaseColumn(CalibrationStatus attribute) {
        return attribute != null ? attribute.value() : null;
    }

    @Override
    public CalibrationStatus convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.isBlank() ? CalibrationStatus.from(dbData) : null;
    }
}
