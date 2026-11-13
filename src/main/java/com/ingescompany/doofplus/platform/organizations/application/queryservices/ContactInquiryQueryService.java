package com.ingescompany.doofplus.platform.organizations.application.queryservices;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.ContactInquiry;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllContactInquiriesQuery;

import java.util.List;

public interface ContactInquiryQueryService {
    List<ContactInquiry> handle(GetAllContactInquiriesQuery query);
}
