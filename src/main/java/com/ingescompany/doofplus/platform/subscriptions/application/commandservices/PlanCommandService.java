package com.ingescompany.doofplus.platform.subscriptions.application.commandservices;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreatePlanCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Application service contract for plan command operations.
 *
 * @since 1.0
 */
public interface PlanCommandService {
    /**
     * Handles creation of a plan.
     *
     * @param command create command containing the plan data
     * @return success when the plan is created, failure when the plan key already exists
     * @see CreatePlanCommand
     */
    Result<Plan, ApplicationError> handle(CreatePlanCommand command);
}
