package com.ingescompany.doofplus.platform.organizations.application.internal.queryservices;

import java.util.Optional;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetContactInquiryByIdQuery;
import com.ingescompany.doofplus.platform.organizations.application.queryservices.ContactInquiryQueryService;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.ContactInquiry;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllContactInquiriesQuery;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.ContactInquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactInquiryQueryServiceImpl implements ContactInquiryQueryService {
    private final ContactInquiryRepository contactInquiryRepository;

    public ContactInquiryQueryServiceImpl(ContactInquiryRepository contactInquiryRepository) {
        this.contactInquiryRepository = contactInquiryRepository;
    }

    @Override
    public List<ContactInquiry> handle(GetAllContactInquiriesQuery query) {
        return contactInquiryRepository.findAll();
    }

    @Override
    public Optional<ContactInquiry> handle(GetContactInquiryByIdQuery query) {
        return contactInquiryRepository.findById(query.id());
    }
}
