package com.ingescompany.doofplus.platform.organizations.domain.repositories;

import java.util.Optional;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.ContactInquiry;

import java.util.List;

public interface ContactInquiryRepository {

    ContactInquiry save(ContactInquiry contactInquiry);

    List<ContactInquiry> findAll();

    Optional<ContactInquiry> findById(Long id);
}
