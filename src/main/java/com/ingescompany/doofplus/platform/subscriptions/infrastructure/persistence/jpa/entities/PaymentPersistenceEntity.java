package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PaymentStatus;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA entity of the {@code payments} table (database diagram of Subscriptions &amp; Payments).
 * The {@code subscription_id} foreign key is mapped by {@link SubscriptionPersistenceEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String invoiceNumber;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(unique = true, length = 100)
    private String gatewayTransactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    private LocalDateTime processedAt;
}
