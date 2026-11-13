package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.OrganizationId;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.entities.SubscriptionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SubscriptionPersistenceRepository extends JpaRepository<SubscriptionPersistenceEntity, Long> {
    List<SubscriptionPersistenceEntity> findAllByOrganizationId(OrganizationId organizationId);
    boolean existsByOrganizationIdAndStatusIn(OrganizationId organizationId, Collection<SubscriptionStatus> statuses);
}
