package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PaymentCard;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.entities.SubscriptionPersistenceEntity;

/**
 * Converts between the {@link Subscription} aggregate and its JPA entity.
 */
public final class SubscriptionPersistenceAssembler {

    private SubscriptionPersistenceAssembler() {
    }

    public static Subscription toDomainFromPersistence(SubscriptionPersistenceEntity entity) {
        return Subscription.reconstitute(
                entity.getId(),
                entity.getOrganizationId(),
                entity.getPlanId(),
                entity.getBillingCycle(),
                entity.getStatus(),
                entity.isAutoRenew(),
                new PaymentCard(entity.getCardBrand(), entity.getCardLast4(), entity.getCardExpiry()),
                entity.getBillingEmail(),
                entity.getCurrentPeriodEnd());
    }

    public static SubscriptionPersistenceEntity toPersistenceFromDomain(Subscription domain) {
        var entity = new SubscriptionPersistenceEntity();
        entity.setId(domain.getId());
        entity.setOrganizationId(domain.getOrganizationId());
        entity.setPlanId(domain.getPlanId());
        entity.setBillingCycle(domain.getBillingCycle());
        entity.setStatus(domain.getStatus());
        entity.setAutoRenew(domain.isAutoRenew());
        entity.setCardBrand(domain.getPaymentCard().brand());
        entity.setCardLast4(domain.getPaymentCard().last4());
        entity.setCardExpiry(domain.getPaymentCard().expiry());
        entity.setBillingEmail(domain.getBillingEmail());
        entity.setCurrentPeriodEnd(domain.getCurrentPeriodEnd());
        return entity;
    }
}
