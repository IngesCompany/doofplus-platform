package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.assemblers;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.entities.Payment;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;

/**
 * Converts between the {@link Payment} entity and its JPA entity.
 */
public final class PaymentPersistenceAssembler {

    private PaymentPersistenceAssembler() {
    }

    public static Payment toDomainFromPersistence(PaymentPersistenceEntity entity) {
        return Payment.reconstitute(
                entity.getId(),
                entity.getInvoiceNumber(),
                new Money(entity.getAmount(), entity.getCurrency()),
                entity.getGatewayTransactionId(),
                entity.getStatus(),
                entity.getProcessedAt());
    }

    public static PaymentPersistenceEntity toPersistenceFromDomain(Payment domain) {
        var entity = new PaymentPersistenceEntity();
        entity.setId(domain.getId());
        entity.setInvoiceNumber(domain.getInvoiceNumber());
        entity.setAmount(domain.getAmount().amount());
        entity.setCurrency(domain.getAmount().currency());
        entity.setGatewayTransactionId(domain.getGatewayTransactionId());
        entity.setStatus(domain.getStatus());
        entity.setProcessedAt(domain.getProcessedAt());
        return entity;
    }
}
