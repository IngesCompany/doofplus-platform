package com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.entities.Payment;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.*;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Subscription of an organization to a plan. It starts as pending, becomes active when the
 * payment is authorized and keeps the access until the end of the paid period when cancelled.
 * The aggregate has no persistence annotations; it is stored through {@code SubscriptionPersistenceEntity}.
 */
@Getter
public class Subscription extends AbstractDomainAggregateRoot<Subscription> {
    private static final String NOT_PENDING_MESSAGE = "Only a pending subscription can be activated";
    private static final String NOT_ACTIVE_MESSAGE = "Only an active subscription can be renewed or cancelled";

    private Long id;
    private OrganizationId organizationId;
    private Long planId;
    private BillingCycle billingCycle;
    private SubscriptionStatus status;
    private boolean autoRenew;
    private PaymentCard paymentCard;
    private BillingEmail billingEmail;
    private LocalDate currentPeriodStart;
    private LocalDate currentPeriodEnd;
    private final List<Payment> payments = new ArrayList<>();

    private Subscription() {}

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
     * Rebuilds a stored subscription.
     *
     * @return the subscription with its persistent identifier
     */
    public static Subscription reconstitute(Long id, OrganizationId organizationId, Long planId, BillingCycle billingCycle,
                                            SubscriptionStatus status, boolean autoRenew, PaymentCard paymentCard,
                                            BillingEmail billingEmail, LocalDate currentPeriodStart,
                                            LocalDate currentPeriodEnd) {
        var subscription = new Subscription();
        subscription.id = id;
        subscription.organizationId = organizationId;
        subscription.planId = planId;
        subscription.billingCycle = billingCycle;
        subscription.status = status;
        subscription.autoRenew = autoRenew;
        subscription.paymentCard = paymentCard;
        subscription.billingEmail = billingEmail;
        subscription.currentPeriodStart = currentPeriodStart;
        subscription.currentPeriodEnd = currentPeriodEnd;
        return subscription;
    }

    /**
     * Activates the subscription once the payment is authorized (US52).
     *
     * @throws IllegalStateException when the subscription is not pending
     */
    public void activate() {
        if (status != SubscriptionStatus.PENDING) throw new IllegalStateException(NOT_PENDING_MESSAGE);
        status = SubscriptionStatus.ACTIVE;
        currentPeriodStart = LocalDate.now();
        currentPeriodEnd = billingCycle.nextPeriodEnd(currentPeriodStart);
    }

    /**
     * Extends the subscription one more billing period (US53).
     *
     * @throws IllegalStateException when the subscription is not active
     */
    public void renew() {
        if (status != SubscriptionStatus.ACTIVE) throw new IllegalStateException(NOT_ACTIVE_MESSAGE);
        currentPeriodStart = currentPeriodEnd;
        currentPeriodEnd = billingCycle.nextPeriodEnd(currentPeriodStart);
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
     * Records a payment of the subscription.
     *
     * @param payment payment to record
     */
    public void recordPayment(Payment payment) {
        if (payment == null) throw new IllegalArgumentException("payment.error.notNull");
        payments.add(payment);
    }

    /**
     * @return an unmodifiable view of the payments of the subscription
     */
    public List<Payment> getPayments() {
        return Collections.unmodifiableList(payments);
    }

    /**
     * @return {@code true} when the subscription is pending or active
     */
    public boolean isCurrent() {
        return status == SubscriptionStatus.PENDING || status == SubscriptionStatus.ACTIVE;
    }
}
