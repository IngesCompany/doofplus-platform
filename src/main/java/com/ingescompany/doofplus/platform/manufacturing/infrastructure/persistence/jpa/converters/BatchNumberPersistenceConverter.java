package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.converters;

import com.ingescompany.doofplus.platform.manufacturing.domain.model.valueobjects.BatchNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BatchNumberPersistenceConverter implements AttributeConverter<BatchNumber, String> {
    @Override
    public String convertToDatabaseColumn(BatchNumber attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public BatchNumber convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new BatchNumber(dbData);
    }
}
