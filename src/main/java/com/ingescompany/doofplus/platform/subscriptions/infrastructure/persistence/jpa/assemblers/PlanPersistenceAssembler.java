package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.entities.PlanPersistenceEntity;

/**
 * Converts between the {@link Plan} aggregate and its JPA entity.
 */
public final class PlanPersistenceAssembler {

    private PlanPersistenceAssembler() {
    }

    public static Plan toDomainFromPersistence(PlanPersistenceEntity entity) {
        return Plan.reconstitute(
                entity.getId(),
                entity.getPlanKey(),
                entity.getName(),
                new Money(entity.getMonthlyPrice(), entity.getCurrency()),
                new Money(entity.getAnnualPrice(), entity.getCurrency()),
                entity.getUserLimit(),
                entity.getIotDeviceLimit());
    }

    public static PlanPersistenceEntity toPersistenceFromDomain(Plan domain) {
        var entity = new PlanPersistenceEntity();
        entity.setId(domain.getId());
        entity.setPlanKey(domain.getPlanKey());
        entity.setName(domain.getName());
        entity.setMonthlyPrice(domain.getMonthlyPrice().amount());
        entity.setAnnualPrice(domain.getAnnualPrice().amount());
        entity.setCurrency(domain.getMonthlyPrice().currency());
        entity.setUserLimit(domain.getUserLimit());
        entity.setIotDeviceLimit(domain.getIotDeviceLimit());
        return entity;
    }
}
