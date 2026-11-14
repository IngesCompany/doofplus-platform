package com.ingescompany.doofplus.platform.subscriptions.domain.model.commands;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.BillingCycle;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.BillingEmail;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.OrganizationId;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PaymentCard;

/**
 * Command to select a plan for an organization (US51). The subscription starts as pending.
 *
 * @param organizationId organization that subscribes
 * @param planId         selected plan
 * @param billingCycle   monthly or annual
 * @param paymentCard    card used to pay
 * @param billingEmail   email that receives the invoices
 */
public record CreateSubscriptionCommand(OrganizationId organizationId, Long planId, BillingCycle billingCycle,
                                        PaymentCard paymentCard, BillingEmail billingEmail) {
    public CreateSubscriptionCommand {
        if (organizationId == null) {
            throw new IllegalArgumentException("subscription.error.organizationId.invalid");
        }
        if (planId == null || planId <= 0) {
            throw new IllegalArgumentException("plan.error.id.invalid");
        }
        if (billingCycle == null) {
            throw new IllegalArgumentException("subscription.error.billingCycle.invalid");
        }
        if (paymentCard == null) {
            throw new IllegalArgumentException("subscription.error.cardBrand.notBlank");
        }
        if (billingEmail == null) {
            throw new IllegalArgumentException("subscription.error.billingEmail.notBlank");
        }
    }
}
