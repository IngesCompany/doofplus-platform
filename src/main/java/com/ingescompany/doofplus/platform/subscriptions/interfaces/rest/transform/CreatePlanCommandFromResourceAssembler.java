package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreatePlanCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PlanKey;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources.CreatePlanResource;

/**
 * Interface layer translator converting {@link CreatePlanResource} into {@link CreatePlanCommand}.
 *
 * @since 1.0
 */
public class CreatePlanCommandFromResourceAssembler {
    /**
     * Converts a CreatePlanResource to a CreatePlanCommand.
     * @param resource CreatePlanResource to convert
     * @return CreatePlanCommand created from the resource
     */
    public static CreatePlanCommand toCommandFromResource(CreatePlanResource resource) {
        return new CreatePlanCommand(
                new PlanKey(resource.planKey()),
                resource.name(),
                new Money(resource.monthlyPrice(), resource.currency()),
                new Money(resource.annualPrice(), resource.currency()),
                resource.userLimit(),
                resource.iotDeviceLimit());
    }
}
