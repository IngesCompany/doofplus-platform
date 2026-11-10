package com.ingescompany.doofplus.platform.subscriptions.application.internal.commandservices;

import com.ingescompany.doofplus.platform.subscriptions.application.commandservices.SubscriptionCommandService;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.ActivateSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CancelSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.PlanRepository;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.SubscriptionRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

/**
 * Application service for subscription command operations.
 * Checks that the selected plan exists and that the organization has a single
 * current subscription, and delegates the state transitions to the aggregate.
 *
 * @since 1.0
 */
@Slf4j
@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {
    private static final List<SubscriptionStatus> CURRENT_STATUSES = List.of(SubscriptionStatus.PENDING, SubscriptionStatus.ACTIVE);
    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository, PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Result<Subscription, ApplicationError> handle(CreateSubscriptionCommand command) {
        if (!planRepository.existsById(command.planId())) {
            log.warn("Plan not found: planId={}", command.planId());
            return Result.failure(ApplicationError.notFound("Plan", command.planId().toString()));
        }
        if (subscriptionRepository.existsByOrganizationIdAndStatusIn(command.organizationId(), CURRENT_STATUSES)) {
            log.warn("Organization already subscribed: organizationId={}", command.organizationId().value());
            return Result.failure(ApplicationError.conflict("Subscription",
                    "Organization %d already has a pending or active subscription".formatted(command.organizationId().value())));
        }
        var createdSubscription = subscriptionRepository.save(new Subscription(command));
        log.info("Subscription created: organizationId={}, planId={}, id={}",
                command.organizationId().value(), command.planId(), createdSubscription.getId());
        return Result.success(createdSubscription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Result<Subscription, ApplicationError> handle(ActivateSubscriptionCommand command) {
        return transition(command.subscriptionId(), Subscription::activate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Result<Subscription, ApplicationError> handle(CancelSubscriptionCommand command) {
        return transition(command.subscriptionId(), Subscription::cancel);
    }

    /**
     * Loads a subscription, applies a state transition and saves it.
     *
     * @param subscriptionId subscription identifier
     * @param transition     aggregate method that changes the state
     * @return the updated subscription, or a failure when it does not exist or the state does not allow the transition
     */
    private Result<Subscription, ApplicationError> transition(Long subscriptionId, Consumer<Subscription> transition) {
        var subscription = subscriptionRepository.findById(subscriptionId);
        if (subscription.isEmpty()) {
            log.warn("Subscription not found: id={}", subscriptionId);
            return Result.failure(ApplicationError.notFound("Subscription", subscriptionId.toString()));
        }
        try {
            transition.accept(subscription.get());
        } catch (IllegalStateException exception) {
            log.warn("Invalid subscription state: id={}, status={}", subscriptionId, subscription.get().getStatus());
            return Result.failure(ApplicationError.businessRuleViolation("subscription-status", exception.getMessage()));
        }
        var updatedSubscription = subscriptionRepository.save(subscription.get());
        log.info("Subscription updated: id={}, status={}", subscriptionId, updatedSubscription.getStatus());
        return Result.success(updatedSubscription);
    }
}
