package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.converters;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RucPersistenceConverter implements AttributeConverter<Ruc, String> {
    @Override
    public String convertToDatabaseColumn(Ruc attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Ruc convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Ruc(dbData);
    }
}
