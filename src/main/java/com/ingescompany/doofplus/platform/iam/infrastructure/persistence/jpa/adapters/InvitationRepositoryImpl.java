package com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.adapters;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.Invitation;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.InvitationStatus;
import com.ingescompany.doofplus.platform.iam.domain.repositories.InvitationRepository;
import com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.assemblers.InvitationPersistenceAssembler;
import com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.repositories.InvitationPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Invitation repository backed by JPA.
 */
@Repository
public class InvitationRepositoryImpl implements InvitationRepository {

    private final InvitationPersistenceRepository repository;

    public InvitationRepositoryImpl(InvitationPersistenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Invitation> findById(Long id) {
        return repository.findById(id).map(InvitationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Invitation> findAll() {
        return repository.findAll().stream().map(InvitationPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public List<Invitation> findAllByOrganizationId(Long organizationId) {
        return repository.findAllByOrganizationId(organizationId).stream()
                .map(InvitationPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public boolean existsPendingByEmail(String email) {
        return repository.existsByEmailAndStatus(email, InvitationStatus.PENDING.name());
    }

    @Override
    public Invitation save(Invitation invitation) {
        var saved = repository.save(InvitationPersistenceAssembler.toPersistenceFromDomain(invitation));
        return InvitationPersistenceAssembler.toDomainFromPersistence(saved);
    }
}
