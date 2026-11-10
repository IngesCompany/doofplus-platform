package com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.*;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.converters.BillingEmailAttributeConverter;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.converters.OrganizationIdAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Subscription of an organization to a plan. It starts as pending, becomes active when the
 * payment is authorized and keeps the access until the end of the paid period when cancelled.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Subscription {
    private static final String NOT_PENDING_MESSAGE = "Only a pending subscription can be activated";
    private static final String NOT_ACTIVE_MESSAGE = "Only an active subscription can be renewed or cancelled";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = OrganizationIdAttributeConverter.class)
    private OrganizationId organizationId;

    @Column(nullable = false)
    private Long planId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private BillingCycle billingCycle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private SubscriptionStatus status;

    @Column(nullable = false)
    private boolean autoRenew;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "brand", column = @Column(name = "card_brand", nullable = false)),
            @AttributeOverride(name = "last4", column = @Column(name = "card_last4", nullable = false, length = 4)),
            @AttributeOverride(name = "expiry", column = @Column(name = "card_expiry", nullable = false, length = 7))
    })
    private PaymentCard paymentCard;

    @Column(nullable = false)
    @Convert(converter = BillingEmailAttributeConverter.class)
    private BillingEmail billingEmail;

    private LocalDate currentPeriodEnd;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    protected Subscription() {}

    /**
     * Creates a pending subscription from a create command (US51).
     *
     * @param command command containing the organization, the plan and the payment data
     */
    public Subscription(CreateSubscriptionCommand command) {
        this.organizationId = command.organizationId();
        this.planId = command.planId();
        this.billingCycle = command.billingCycle();
        this.paymentCard = command.paymentCard();
        this.billingEmail = command.billingEmail();
        this.status = SubscriptionStatus.PENDING;
        this.autoRenew = true;
    }

    /**
     * Activates the subscription once the payment is authorized (US52).
     *
     * @throws IllegalStateException when the subscription is not pending
     */
    public void activate() {
        if (status != SubscriptionStatus.PENDING) throw new IllegalStateException(NOT_PENDING_MESSAGE);
        status = SubscriptionStatus.ACTIVE;
        currentPeriodEnd = billingCycle.nextPeriodEnd(LocalDate.now());
    }

    /**
     * Extends the subscription one more billing period (US53).
     *
     * @throws IllegalStateException when the subscription is not active
     */
    public void renew() {
        if (status != SubscriptionStatus.ACTIVE) throw new IllegalStateException(NOT_ACTIVE_MESSAGE);
        currentPeriodEnd = billingCycle.nextPeriodEnd(currentPeriodEnd);
    }

    /**
     * Cancels the subscription; the access stays until the end of the paid period (US53).
     *
     * @throws IllegalStateException when the subscription is not active
     */
    public void cancel() {
        if (status != SubscriptionStatus.ACTIVE) throw new IllegalStateException(NOT_ACTIVE_MESSAGE);
        status = SubscriptionStatus.CANCELLED;
        autoRenew = false;
    }

    /**
     * @return {@code true} when the subscription is pending or active
     */
    public boolean isCurrent() {
        return status == SubscriptionStatus.PENDING || status == SubscriptionStatus.ACTIVE;
    }
}
