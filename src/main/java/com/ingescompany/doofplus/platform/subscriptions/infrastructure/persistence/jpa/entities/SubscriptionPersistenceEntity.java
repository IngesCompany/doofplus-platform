package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.BillingCycle;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.BillingEmail;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.OrganizationId;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.converters.BillingEmailAttributeConverter;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.converters.OrganizationIdAttributeConverter;
import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity of the {@code subscriptions} table (database diagram of Subscriptions &amp; Payments).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class SubscriptionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    @Convert(converter = OrganizationIdAttributeConverter.class)
    private OrganizationId organizationId;

    @Column(nullable = false)
    private Long planId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private BillingCycle billingCycle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SubscriptionStatus status;

    private LocalDate currentPeriodStart;

    private LocalDate currentPeriodEnd;

    @Column(nullable = false)
    private boolean autoRenew;

    @Column(nullable = false, length = 20)
    private String cardBrand;

    @Column(nullable = false, length = 4)
    private String cardLast4;

    @Column(nullable = false, length = 7)
    private String cardExpiry;

    @Column(nullable = false, length = 254)
    @Convert(converter = BillingEmailAttributeConverter.class)
    private BillingEmail billingEmail;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "subscription_id", nullable = false)
    private List<PaymentPersistenceEntity> payments = new ArrayList<>();
}
