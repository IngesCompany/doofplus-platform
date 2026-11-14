package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.AlertCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AlertCodeAttributeConverter implements AttributeConverter<AlertCode, String> {

    @Override
    public String convertToDatabaseColumn(AlertCode attribute) {
        return attribute != null ? attribute.value() : null;
    }

    @Override
    public AlertCode convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.isBlank() ? new AlertCode(dbData) : null;
    }
}
