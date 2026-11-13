package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Profile;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.ProfileRepository;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.assemblers.ProfilePersistenceAssembler;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.repositories.ProfilePersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfilePersistenceRepository profilePersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public ProfileRepositoryImpl(ProfilePersistenceRepository profilePersistenceRepository,
                                 DomainEventPublisher domainEventPublisher) {
        this.profilePersistenceRepository = profilePersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Profile save(Profile profile) {
        var saved = profilePersistenceRepository.save(ProfilePersistenceAssembler.toPersistenceFromDomain(profile));
        domainEventPublisher.publishAndClearEvents(profile);
        return ProfilePersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return profilePersistenceRepository.findById(id).map(ProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Profile> findByUserId(Long userId) {
        return profilePersistenceRepository.findByUserId(userId)
                .map(ProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Profile> findAll() {
        return profilePersistenceRepository.findAll().stream()
                .map(ProfilePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
