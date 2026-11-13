package com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreatePlanCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.BillingCycle;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PlanKey;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlanTest {

    private static Plan plan(int iotDeviceLimit) {
        return new Plan(new CreatePlanCommand(new PlanKey("standard-lab"), "Standard Lab",
                new Money(new BigDecimal("49.90"), "USD"), new Money(new BigDecimal("499.00"), "USD"), 10, iotDeviceLimit));
    }

    @Test
    void getPriceShouldReturnThePriceOfTheBillingCycle() {
        var plan = plan(5);

        assertThat(plan.getPrice(BillingCycle.MONTHLY).amount()).isEqualByComparingTo("49.90");
        assertThat(plan.getPrice(BillingCycle.ANNUAL).amount()).isEqualByComparingTo("499.00");
    }

    @Test
    void allowsOneMoreIotDeviceShouldRespectTheLimit() {
        var plan = plan(5);

        assertThat(plan.allowsOneMoreIotDevice(4)).isTrue();
        assertThat(plan.allowsOneMoreIotDevice(5)).isFalse();
    }

    @Test
    void allowsOneMoreIotDeviceShouldAllowAnyNumberWhenTheLimitIsZero() {
        assertThat(plan(0).allowsOneMoreIotDevice(1_000)).isTrue();
    }

    @Test
    void createPlanCommandShouldAcceptZeroLimitsAndRejectNegativeOnes() {
        var price = new Money(BigDecimal.TEN, "USD");

        assertThat(new CreatePlanCommand(new PlanKey("enterprise"), "Enterprise", price, price, 0, 0).userLimit()).isZero();
        assertThatThrownBy(() -> new CreatePlanCommand(new PlanKey("enterprise"), "Enterprise", price, price, -1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("plan.error.limits.invalid");
    }
}
