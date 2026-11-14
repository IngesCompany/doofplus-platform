package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.BillingCycle;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.BillingEmail;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.OrganizationId;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PaymentCard;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources.CreateSubscriptionResource;

/**
 * Interface layer translator converting {@link CreateSubscriptionResource} into {@link CreateSubscriptionCommand}.
 *
 * @since 1.0
 */
public class CreateSubscriptionCommandFromResourceAssembler {
    /**
     * Converts a CreateSubscriptionResource to a CreateSubscriptionCommand.
     * @param resource CreateSubscriptionResource to convert
     * @return CreateSubscriptionCommand created from the resource
     */
    public static CreateSubscriptionCommand toCommandFromResource(CreateSubscriptionResource resource) {
        return new CreateSubscriptionCommand(
                new OrganizationId(resource.organizationId()),
                resource.planId(),
                BillingCycle.valueOf(resource.billingCycle()),
                new PaymentCard(resource.cardBrand(), resource.cardLast4(), resource.cardExpiry()),
                new BillingEmail(resource.billingEmail()));
    }
}
