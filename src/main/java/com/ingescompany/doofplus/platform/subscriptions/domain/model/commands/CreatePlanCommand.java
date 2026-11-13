package com.ingescompany.doofplus.platform.subscriptions.domain.model.commands;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PlanKey;

/**
 * Command to create a subscription plan.
 *
 * @param planKey        unique business key of the plan
 * @param name           display name
 * @param monthlyPrice   price of the monthly cycle
 * @param annualPrice    price of the annual cycle
 * @param userLimit      maximum number of users (0 means unlimited)
 * @param iotDeviceLimit maximum number of IoT devices (0 means unlimited)
 */
public record CreatePlanCommand(PlanKey planKey, String name, Money monthlyPrice, Money annualPrice,
                                int userLimit, int iotDeviceLimit) {
    public CreatePlanCommand {
        if (planKey == null) {
            throw new IllegalArgumentException("plan.error.planKey.notBlank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("plan.error.name.notBlank");
        }
        if (monthlyPrice == null || annualPrice == null) {
            throw new IllegalArgumentException("money.error.amount.invalid");
        }
        if (userLimit < 0 || iotDeviceLimit < 0) {
            throw new IllegalArgumentException("plan.error.limits.invalid");
        }
    }
}
