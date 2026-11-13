package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.DeviceId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DeviceIdAttributeConverter implements AttributeConverter<DeviceId, String> {

    @Override
    public String convertToDatabaseColumn(DeviceId attribute) {
        return attribute != null ? attribute.value() : null;
    }

    @Override
    public DeviceId convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.isBlank() ? new DeviceId(dbData) : null;
    }
}
