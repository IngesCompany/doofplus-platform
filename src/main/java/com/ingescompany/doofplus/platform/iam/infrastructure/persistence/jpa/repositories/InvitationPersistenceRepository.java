package com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.entities.InvitationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data repository of the invitation rows.
 */
@Repository
public interface InvitationPersistenceRepository extends JpaRepository<InvitationPersistenceEntity, Long> {
    List<InvitationPersistenceEntity> findAllByOrganizationId(Long organizationId);

    boolean existsByEmailAndStatus(String email, String status);

    Optional<InvitationPersistenceEntity> findFirstByEmailAndStatus(String email, String status);
}
