package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

/**
 * Request resource used to change the plan and the billing cycle of a subscription.
 *
 * @param planId       identifier of the new plan
 * @param billingCycle new billing cycle, MONTHLY or ANNUAL
 */
@Schema(description = "Request resource used to change the plan and the billing cycle of a subscription")
public record ChangeSubscriptionPlanResource(
        @Schema(description = "Plan identifier", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "{plan.error.id.invalid}")
        @Positive(message = "{plan.error.id.invalid}")
        Long planId,
        @Schema(description = "Billing cycle", example = "ANNUAL", allowableValues = {"MONTHLY", "ANNUAL"}, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "{subscription.error.billingCycle.invalid}")
        @Pattern(regexp = "^(MONTHLY|ANNUAL)$", message = "{subscription.error.billingCycle.invalid}")
        String billingCycle) {
}
