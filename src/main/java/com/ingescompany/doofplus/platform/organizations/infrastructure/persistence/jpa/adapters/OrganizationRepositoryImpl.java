package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.OrganizationRepository;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.assemblers.OrganizationPersistenceAssembler;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.repositories.OrganizationPersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private final OrganizationPersistenceRepository organizationPersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public OrganizationRepositoryImpl(OrganizationPersistenceRepository organizationPersistenceRepository,
                                      DomainEventPublisher domainEventPublisher) {
        this.organizationPersistenceRepository = organizationPersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Organization save(Organization organization) {
        var saved = organizationPersistenceRepository.save(
                OrganizationPersistenceAssembler.toPersistenceFromDomain(organization));
        domainEventPublisher.publishAndClearEvents(organization);
        return OrganizationPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public Optional<Organization> findById(Long id) {
        return organizationPersistenceRepository.findById(id)
                .map(OrganizationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsByRuc(Ruc ruc) {
        return organizationPersistenceRepository.existsByRuc(ruc);
    }
}
