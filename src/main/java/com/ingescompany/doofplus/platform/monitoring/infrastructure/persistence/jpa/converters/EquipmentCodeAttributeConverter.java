package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.EquipmentCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EquipmentCodeAttributeConverter implements AttributeConverter<EquipmentCode, String> {

    @Override
    public String convertToDatabaseColumn(EquipmentCode attribute) {
        return attribute != null ? attribute.value() : null;
    }

    @Override
    public EquipmentCode convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.isBlank() ? new EquipmentCode(dbData) : null;
    }
}
