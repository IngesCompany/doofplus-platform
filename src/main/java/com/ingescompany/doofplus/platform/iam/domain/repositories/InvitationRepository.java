package com.ingescompany.doofplus.platform.iam.domain.repositories;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.Invitation;

import java.util.List;
import java.util.Optional;

/**
 * Repository of the invitations.
 */
public interface InvitationRepository {
    Optional<Invitation> findById(Long id);

    List<Invitation> findAll();

    List<Invitation> findAllByOrganizationId(Long organizationId);

    boolean existsPendingByEmail(String email);

    Invitation save(Invitation invitation);
}
