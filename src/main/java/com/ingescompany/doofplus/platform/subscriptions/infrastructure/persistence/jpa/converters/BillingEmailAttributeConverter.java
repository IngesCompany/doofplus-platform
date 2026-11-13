package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.converters;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.BillingEmail;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BillingEmailAttributeConverter implements AttributeConverter<BillingEmail, String> {
    @Override
    public String convertToDatabaseColumn(BillingEmail attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public BillingEmail convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new BillingEmail(dbData);
    }
}
