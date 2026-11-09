package com.ingescompany.doofplus.platform.monitoring.infrastructure.persistence.jpa.converters;

import com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects.Facility;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FacilityAttributeConverter implements AttributeConverter<Facility, String> {

    @Override
    public String convertToDatabaseColumn(Facility attribute) {
        return attribute != null ? attribute.value() : null;
    }

    @Override
    public Facility convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.isBlank() ? new Facility(dbData) : null;
    }
}
