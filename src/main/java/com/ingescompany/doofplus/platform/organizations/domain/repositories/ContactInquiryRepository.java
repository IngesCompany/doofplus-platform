package com.ingescompany.doofplus.platform.organizations.domain.repositories;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.ContactInquiry;

import java.util.List;

public interface ContactInquiryRepository {

    ContactInquiry save(ContactInquiry contactInquiry);

    List<ContactInquiry> findAll();
}
