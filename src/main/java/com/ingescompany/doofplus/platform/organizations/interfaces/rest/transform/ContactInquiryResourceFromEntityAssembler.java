package com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.ContactInquiry;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.ContactInquiryResource;

import java.util.Locale;

public final class ContactInquiryResourceFromEntityAssembler {

    private ContactInquiryResourceFromEntityAssembler() {
    }

    public static ContactInquiryResource toResourceFromEntity(ContactInquiry entity) {
        if (entity == null) return null;
        return new ContactInquiryResource(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getMessage(),
                entity.getStatus() != null ? entity.getStatus().name().toLowerCase(Locale.ROOT) : null,
                entity.getReceivedAt());
    }
}
