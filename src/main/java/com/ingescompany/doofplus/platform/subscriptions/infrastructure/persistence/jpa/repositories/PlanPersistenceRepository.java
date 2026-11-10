package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PlanKey;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.entities.PlanPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanPersistenceRepository extends JpaRepository<PlanPersistenceEntity, Long> {
    boolean existsByPlanKey(PlanKey planKey);
}
