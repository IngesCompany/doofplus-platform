package com.ingescompany.doofplus.platform.subscriptions.application.commandservices;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.ActivateSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CancelSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.RenewSubscriptionCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Application service contract for subscription command operations.
 *
 * @since 1.0
 */
public interface SubscriptionCommandService {
    /**
     * Handles the selection of a plan by an organization (US51).
     *
     * @param command create command
     * @return the pending subscription, or a failure when the plan does not exist or the organization is already subscribed
     */
    Result<Subscription, ApplicationError> handle(CreateSubscriptionCommand command);

    /**
     * Handles the activation of a pending subscription once the payment is authorized (US52).
     *
     * @param command activate command
     * @return the active subscription, or a failure when it does not exist or is not pending
     */
    Result<Subscription, ApplicationError> handle(ActivateSubscriptionCommand command);

    /**
     * Handles the renewal of an active subscription for one more billing period (US53).
     *
     * @param command renew command
     * @return the renewed subscription, or a failure when it does not exist or is not active
     */
    Result<Subscription, ApplicationError> handle(RenewSubscriptionCommand command);

    /**
     * Handles the cancellation of an active subscription (US53).
     *
     * @param command cancel command
     * @return the cancelled subscription, or a failure when it does not exist or is not active
     */
    Result<Subscription, ApplicationError> handle(CancelSubscriptionCommand command);
}
