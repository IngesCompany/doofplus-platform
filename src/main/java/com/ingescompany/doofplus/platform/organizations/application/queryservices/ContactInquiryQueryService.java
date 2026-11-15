package com.ingescompany.doofplus.platform.organizations.application.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetContactInquiryByIdQuery;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.ContactInquiry;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllContactInquiriesQuery;

import java.util.List;

public interface ContactInquiryQueryService {
    List<ContactInquiry> handle(GetAllContactInquiriesQuery query);

    /**
     * Gets a ContactInquiry by its identifier.
     *
     * @param query the query
     * @return the ContactInquiry, or empty when it does not exist
     */
    Optional<ContactInquiry> handle(GetContactInquiryByIdQuery query);
}
