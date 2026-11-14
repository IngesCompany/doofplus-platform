package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.UserProfile;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.UserProfileRepository;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.assemblers.UserProfilePersistenceAssembler;
import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.repositories.UserProfilePersistenceRepository;
import com.ingescompany.doofplus.platform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserProfileRepositoryImpl implements UserProfileRepository {

    private final UserProfilePersistenceRepository userProfilePersistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public UserProfileRepositoryImpl(UserProfilePersistenceRepository userProfilePersistenceRepository,
                                 DomainEventPublisher domainEventPublisher) {
        this.userProfilePersistenceRepository = userProfilePersistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public UserProfile save(UserProfile profile) {
        var saved = userProfilePersistenceRepository.save(UserProfilePersistenceAssembler.toPersistenceFromDomain(profile));
        domainEventPublisher.publishAndClearEvents(profile);
        return UserProfilePersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public Optional<UserProfile> findById(Long id) {
        return userProfilePersistenceRepository.findById(id).map(UserProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<UserProfile> findByUserId(Long userId) {
        return userProfilePersistenceRepository.findByUserId(userId)
                .map(UserProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<UserProfile> findAll() {
        return userProfilePersistenceRepository.findAll().stream()
                .map(UserProfilePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
