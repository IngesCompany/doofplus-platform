package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.ContactInquiry;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.ContactInquiryPersistenceEntity;

public final class ContactInquiryPersistenceAssembler {

    private ContactInquiryPersistenceAssembler() {
    }

    public static ContactInquiry toDomainFromPersistence(ContactInquiryPersistenceEntity entity) {
        if (entity == null) return null;
        return ContactInquiry.reconstitute(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getMessage(),
                entity.getStatus(),
                entity.getReceivedAt());
    }

    public static ContactInquiryPersistenceEntity toPersistenceFromDomain(ContactInquiry domain) {
        if (domain == null) return null;
        var entity = new ContactInquiryPersistenceEntity();
        entity.setId(domain.getId());
        entity.setFullName(domain.getFullName());
        entity.setEmail(domain.getEmail());
        entity.setMessage(domain.getMessage());
        entity.setStatus(domain.getStatus());
        entity.setReceivedAt(domain.getReceivedAt());
        return entity;
    }
}
