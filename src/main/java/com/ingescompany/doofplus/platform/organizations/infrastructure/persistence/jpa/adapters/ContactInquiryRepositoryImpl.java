package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.ContactInquiry;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.ContactInquiryRepository;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.assemblers.ContactInquiryPersistenceAssembler;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.repositories.ContactInquiryPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactInquiryRepositoryImpl implements ContactInquiryRepository {

    private final ContactInquiryPersistenceRepository contactInquiryPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public ContactInquiryRepositoryImpl(ContactInquiryPersistenceRepository contactInquiryPersistenceRepository,
                                        DomainEventPublisher domainEventPublisher) {
        this.contactInquiryPersistenceRepository = contactInquiryPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public ContactInquiry save(ContactInquiry contactInquiry) {
        var saved = contactInquiryPersistenceRepository.save(
                ContactInquiryPersistenceAssembler.toPersistenceFromDomain(contactInquiry));
        domainEventPublisher.publishAndClearEvents(contactInquiry);
        return ContactInquiryPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public List<ContactInquiry> findAll() {
        return contactInquiryPersistenceRepository.findAllByOrderByReceivedAtDesc().stream()
                .map(ContactInquiryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
