package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources.PaymentResource;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources.SubscriptionResource;

/**
 * Interface layer translator converting {@link Subscription} aggregates into {@link SubscriptionResource}.
 *
 * @since 1.0
 */
public class SubscriptionResourceFromEntityAssembler {
    /**
     * Converts a Subscription entity to a SubscriptionResource.
     * @param entity Subscription entity to convert
     * @return SubscriptionResource created from the entity
     */
    public static SubscriptionResource toResourceFromEntity(Subscription entity) {
        return new SubscriptionResource(
                entity.getId(),
                entity.getOrganizationId().value(),
                entity.getPlanId(),
                entity.getBillingCycle().name(),
                entity.getStatus().name(),
                entity.isAutoRenew(),
                entity.getPaymentCard().brand(),
                entity.getPaymentCard().last4(),
                entity.getPaymentCard().expiry(),
                entity.getBillingEmail().value(),
                entity.getCurrentPeriodStart(),
                entity.getCurrentPeriodEnd(),
                entity.getPayments().stream()
                        .map(payment -> new PaymentResource(
                                payment.getId(),
                                payment.getInvoiceNumber(),
                                payment.getAmount().amount(),
                                payment.getAmount().currency(),
                                payment.getGatewayTransactionId(),
                                payment.getStatus().name(),
                                payment.getProcessedAt()))
                        .toList());
    }
}
