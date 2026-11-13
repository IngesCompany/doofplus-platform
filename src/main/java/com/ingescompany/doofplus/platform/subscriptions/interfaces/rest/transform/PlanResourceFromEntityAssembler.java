package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources.PlanResource;

/**
 * Interface layer translator converting {@link Plan} aggregates into {@link PlanResource}.
 *
 * @since 1.0
 */
public class PlanResourceFromEntityAssembler {
    /**
     * Converts a Plan entity to a PlanResource.
     * @param entity Plan entity to convert
     * @return PlanResource created from the entity
     */
    public static PlanResource toResourceFromEntity(Plan entity) {
        return new PlanResource(
                entity.getId(),
                entity.getPlanKey().value(),
                entity.getName(),
                entity.getMonthlyPrice().amount(),
                entity.getAnnualPrice().amount(),
                entity.getMonthlyPrice().currency(),
                entity.getUserLimit(),
                entity.getIotDeviceLimit());
    }
}
