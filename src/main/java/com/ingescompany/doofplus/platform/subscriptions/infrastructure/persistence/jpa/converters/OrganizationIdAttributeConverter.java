package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.converters;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.OrganizationId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class OrganizationIdAttributeConverter implements AttributeConverter<OrganizationId, Long> {
    @Override
    public Long convertToDatabaseColumn(OrganizationId attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public OrganizationId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new OrganizationId(dbData);
    }
}
