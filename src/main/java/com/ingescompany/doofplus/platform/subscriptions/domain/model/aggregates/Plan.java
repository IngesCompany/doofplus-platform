package com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreatePlanCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.BillingCycle;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PlanKey;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Subscription plan offered by DoofPlus, with its prices and the user and IoT device limits.
 * The aggregate has no persistence annotations; it is stored through {@code PlanPersistenceEntity}.
 */
@Getter
public class Plan extends AbstractDomainAggregateRoot<Plan> {
    private Long id;
    private PlanKey planKey;
    private String name;
    private Money monthlyPrice;
    private Money annualPrice;
    private int userLimit;
    private int iotDeviceLimit;

    private Plan() {}

    /**
     * Creates a plan aggregate from a create command.
     *
     * @param command command containing the plan data
     */
    public Plan(CreatePlanCommand command) {
        this.planKey = command.planKey();
        this.name = command.name();
        this.monthlyPrice = command.monthlyPrice();
        this.annualPrice = command.annualPrice();
        this.userLimit = command.userLimit();
        this.iotDeviceLimit = command.iotDeviceLimit();
    }

    /**
     * Rebuilds a stored plan.
     *
     * @return the plan with its persistent identifier
     */
    public static Plan reconstitute(Long id, PlanKey planKey, String name, Money monthlyPrice, Money annualPrice,
                                    int userLimit, int iotDeviceLimit) {
        var plan = new Plan();
        plan.id = id;
        plan.planKey = planKey;
        plan.name = name;
        plan.monthlyPrice = monthlyPrice;
        plan.annualPrice = annualPrice;
        plan.userLimit = userLimit;
        plan.iotDeviceLimit = iotDeviceLimit;
        return plan;
    }

    /**
     * @param cycle billing cycle
     * @return price of the plan for the cycle
     */
    public Money getPrice(BillingCycle cycle) {
        return cycle == BillingCycle.MONTHLY ? monthlyPrice : annualPrice;
    }

    /**
     * Checks the plan limit before registering one more IoT device (US51, scenario 2).
     *
     * @param registeredDevices devices already registered by the organization
     * @return {@code true} when one more device fits in the plan
     */
    public boolean allowsOneMoreIotDevice(int registeredDevices) {
        return registeredDevices < iotDeviceLimit;
    }
}
